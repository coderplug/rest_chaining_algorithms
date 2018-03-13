package main.chaining;

import main.data.Data;
import main.data.Result;
import main.data.Rule;
import main.data.Trace;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "chainingQuery")
public class ForwardChaining extends AbstractChaining {

    String step;
    private int lineCount;
    private List<String> facts;

    public ForwardChaining(){
        super();
        lineCount = 0;
    }

    public ForwardChaining(Data data) {
        super(data);
        lineCount = 0;
        facts = new LinkedList<>(data.getFacts());
    }

    public void execute() {
        List<Rule> ruleList = new LinkedList<>();
        int iterationNumber = 0;
        boolean facts_changed;
        Trace trace = getTrace();
        Data data = getData();
        String goal = data.getGoal();
        List<Rule> rules = data.getRules();
        if (facts.contains(goal)) {
            setResult(new Result(true, ruleList, data));
            return;
        }
        do {
            iterationNumber++;
            trace.addToTrace("  ITERATION " + iterationNumber);
            facts_changed = false;
            for (Rule rule : rules) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("    " + rule.toString());
                if (!rule.getFlag1() && !rule.getFlag2()) {
                    //Checks if facts already contains current rule antecedents (rule conditions)
                    if (facts.containsAll(rule.getAntecedents())) {
                        //Checks if facts doesn't contain consequent (rule result)
                        if (!facts.contains(rule.getConsequent())) {
                            rule.setFlag1(true);
                            ruleList.add(rule);
                            facts.add(rule.getConsequent());
                            trace.addToTrace(stringBuilder.toString() + " apply. Raise flag1. Facts "
                                    + listFacts() + ".");
                            facts_changed = true;
                            if (rule.getConsequent().equals(goal)) {
                                trace.addToTrace("    Goal achieved." + NL);
                                setResult(new Result(true, ruleList, data));
                                return;
                            }
                        }
                        else{
                            rule.setFlag2(true);
                            trace.addToTrace(stringBuilder.toString() + " not applied, because RHS in facts. Raise flag2.");
                        }
                    } else {
                        stringBuilder.append(" not applied, because of lacking ");
                        for(String antecedent: rule.getAntecedents()){
                            if (!getFacts().contains(antecedent)) {
                                stringBuilder.append(antecedent);
                                break;
                            }
                        }
                        trace.addToTrace(stringBuilder.toString() + ".");
                    }
                } else if (rule.getFlag2()){
                    trace.addToTrace(stringBuilder.toString() + " skip, because flag2 raised.");
                } else {
                    trace.addToTrace(stringBuilder.toString() + " skip, because flag1 raised.");
                }
                if (facts_changed) {
                    break;
                }
            }
            trace.addToTrace("");
        } while (facts_changed);
        setResult(new Result(false, ruleList, data));
        return;
    }
}
