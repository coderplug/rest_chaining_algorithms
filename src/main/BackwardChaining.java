package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BackwardChaining extends AbstractChaining {

    private List<String> goals;
    private List<Rule> resultPath;
    private int recursionLevel;
    private int lineCount;
    //private List<String> facts;

    public BackwardChaining(String goal, List<Rule> rules, List<String> facts, StringBuilder sb) {
        super(goal, rules, facts, sb);
        resultPath = new LinkedList<>();
        goals = new LinkedList<>();
        lineCount = 0;
        recursionLevel = 0;
        //facts = new LinkedList<>();
    }

    protected ChainingProcessResult doExecute(){
        boolean success = backwardChaining(getGoal());
        getSb().append(NL);
        return new ChainingProcessResult(getSb(), success, resultPath, getGoal());
    }
    //Recursive function
    private boolean backwardChaining(String currentGoal) {
        recursionLevel++;
        //Checks whether facts have current goal
        if (getFacts().contains(currentGoal)) {
            getSb().append(getRecursionLevel()).append("Goal ").append(currentGoal).append(".");
            getSb().append(" Fact.").append(NL);
            recursionLevel--;
            return true;
        }
        //Adding current goal to goals list
        goals.add(currentGoal);
        //Skimming through rules
        for (Rule rule : getRules()) {
            //Checks if rule is not used and currentGoal is rules result
            if (!rule.getFlag1() && rule.getConsequent().equals(currentGoal)) {
                List<String> antecedents = rule.getAntecedents();
                boolean antecendentsExists = true;
                //Information about current goal, used rule and new goals
                getSb().append(getRecursionLevel()).append("Goal ").append(currentGoal).append(".")
                        .append(" Take ").append(rule.toString()).append(".")
                        .append(" New goals ").append(listAntecendants(rule)).append(".").append(NL);
                //Iterating through antecendants to check whether they exist
                for (String antecedent : antecedents) {
                    //Checks whether goal finding fails  -----NESUPRANTU ŠITO
                    if (!goals.contains(antecedent)){
                        if (!backwardChaining(antecedent)) {
                            antecendentsExists = false;
                            break;
                        }
                    } else {
                        recursionLevel++;
                        getSb().append(getRecursionLevel()).append("Goal ").append(antecedent).append(". Loop.").append(NL);
                        recursionLevel--;
                        antecendentsExists = false;
                        break;
                    }
                }
                if (antecendentsExists) {
                    rule.setFlag1(true);
                    getFacts().add(rule.getConsequent());
                    resultPath.add(rule);
                    getSb().append(getRecursionLevel()).append("Goal ").append(currentGoal)
                            .append(". New fact: ").append(rule.getConsequent())
                            .append(". Facts: ").append(listFacts()).append(".").append(NL);
                    recursionLevel--;
                    goals.remove(currentGoal);
                    return true;
                }
            }
        }
        goals.remove(currentGoal);
        for(Rule rule: getRules()){
            if(rule.getFlag1()){
                rule.setFlag1(false);
                resultPath.remove(rule);
                getFacts().remove(rule.getConsequent());
            }
        }
        getSb().append(getRecursionLevel()).append("Goal ").append(currentGoal).append(".");
        getSb().append(" Fact cannot be derived").append(".").append(NL);
        recursionLevel--;
        return false;
    }

    public String getResultPath() {
        if (resultPath.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        getSb().append("  Path: {");
        for (Rule rule : resultPath) {
            sb.append("R").append(Integer.toString(rule.getNumber())).append(", ");
        }
        if (sb.lastIndexOf(", ") != -1) {
            sb.delete(getSb().lastIndexOf(", "), getSb().length());
        }
        sb.append("}");
        return sb.toString();
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
