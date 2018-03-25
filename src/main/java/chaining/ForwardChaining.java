package chaining;

import data.Data;
import data.Result;
import data.Trace;
import data.entity.Antecedent;
import data.entity.Rule;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "chainingQuery")
public class ForwardChaining extends AbstractChaining {

    public ForwardChaining(){
        super();
    }

    public ForwardChaining(Data data) {
        super(data);
    }

    public void execute() {
        List<Rule> ruleList = new LinkedList<>();
        int iterationNumber = 0;
        boolean facts_changed;
        Trace trace = getTrace();
        Data data = getData();
        String goal = data.getGoal();
        List<Rule> rules = data.getRules();
        List<String> facts = getFacts();
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

                /*
                Checks if flag1 or flag2 is up
                flag1 - used rule
                flag2 - not applied because result is already in the facts
                 */
                if (!rule.getFlag1() && !rule.getFlag2()) {
                    /*
                    Checks if facts contains rule antecedents (rule conditions)
                     */
                    List<String> antecedents = new LinkedList<>();
                    for (Antecedent antecedent: rule.getAntecedents())
                    {
                        antecedents.add(antecedent.getName());
                    }
                    if (facts.containsAll(antecedents)) {
                        /*
                        Checks if facts doesn't contain consequent (rule result)
                         */
                        if (!facts.contains(rule.getConsequent())) {
                            //Marked as used
                            rule.setFlag1(true);
                            ruleList.add(rule);
                            //Result added to facts
                            facts.add(rule.getConsequent());

                            trace.addToTrace(stringBuilder.toString() + " apply. Raise flag1. Facts "
                                    + listFacts() + ".");
                            facts_changed = true;
                            //Checks if last rules result is goal
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
                        for(Antecedent antecedent: rule.getAntecedents()){
                            if (!getFacts().contains(antecedent.getName())) {
                                stringBuilder.append(antecedent.getName());
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
    }
}
