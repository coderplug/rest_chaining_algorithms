package main;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "chainingQuery")
public class BackwardChaining2 extends AbstractChaining2 {

    private List<String> goals;
    private List<Rule> resultPath;
    private int recursionLevel;
    private int lineCount;

    public BackwardChaining2() {
        super();
    }

    public BackwardChaining2(Data data) {
        super(data);
        resultPath = new LinkedList<>();
        goals = new LinkedList<>();
        lineCount = 0;
        recursionLevel = 0;
    }

    protected void execute(){
        boolean success = backwardChaining(getData().getGoal());
        setResult(new Result(success, resultPath, getData()));
    }
    //Recursive function
    private boolean backwardChaining(String currentGoal) {
        //List<String> facts = getData().getFacts();
        Trace trace = getTrace();
        List<Rule> rules = getData().getRules();
        List<String> facts = getFacts();
        recursionLevel++;
        //Checks whether facts have current goal
        if (facts.contains(currentGoal)) {
            trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal + "." +
                    " Fact.");
            recursionLevel--;
            return true;
        }
        //Adding current goal to goals list
        goals.add(currentGoal);
        //Skimming through rules
        for (Rule rule : rules) {
            //Checks if rule is not used and currentGoal is rules result
            if (!rule.getFlag1() && rule.getConsequent().equals(currentGoal)) {
                List<String> antecedents = rule.getAntecedents();
                boolean antecendentsExists = true;
                //Information about current goal, used rule and new goals
                trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal + "."
                        + " Take " + rule.toString() + "."
                        + " New goals " + rule.listAntecedents() + ".");
                //Iterating through antecendants to check whether they exist
                for (String antecedent : antecedents) {
                    //Checks whether goal finding fails
                    if (!goals.contains(antecedent)){
                        if (!backwardChaining(antecedent)) {
                            antecendentsExists = false;
                            break;
                        }
                    } else {
                        recursionLevel++;
                        trace.addToTrace(getRecursionLevel() + "Goal " + antecedent + ". Loop.");
                        recursionLevel--;
                        antecendentsExists = false;
                        break;
                    }
                }
                if (antecendentsExists) {
                    rule.setFlag1(true);
                    facts.add(rule.getConsequent());
                    resultPath.add(rule);
                    trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal
                                    + ". New fact: " + rule.getConsequent()
                                    + ". Facts: " + listFacts() + ".");
                    recursionLevel--;
                    goals.remove(currentGoal);
                    return true;
                }
            }
        }
        goals.remove(currentGoal);
        for(Rule rule: rules){
            if(rule.getFlag1()){
                rule.setFlag1(false);
                resultPath.remove(rule);
                facts.remove(rule.getConsequent());
            }
        }
        trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal + "."
                + " Fact cannot be derived" + ".");
        recursionLevel--;
        return false;
    }

    public String getRecursionLevel() {
        StringBuilder level = new StringBuilder();
        String lineNumber = Integer.toString(++lineCount);

        for (int i = 0; i < 3 - lineNumber.length(); i++) {
            level.append(" ");
        }
        level.append(lineNumber + ") ");

        for (int i = 1; i < recursionLevel; i++) {
            level.append("-");
        }

        return level.toString();
    }
}
