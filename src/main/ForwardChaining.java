package main;

import main.AbstractChaining;

import java.util.LinkedList;
import java.util.List;

public class ForwardChaining extends AbstractChaining {

    String step;
    private int lineCount;

    public ForwardChaining(String goal, List<Rule> rules, List<String> facts, StringBuilder sb) {
        super(goal , rules, facts, sb);
        lineCount = 0;
    }

    protected ChainingProcessResult doExecute() {
        List<Rule> ruleList = new LinkedList<>();
        List<Rule> ruleSequence = new LinkedList<Rule>();
        int iterationNumber = 0;
        boolean facts_changed;
        StringBuilder stringBuilder = getSb();
        String goal = getGoal();
        if (getFacts().contains(goal)) {
            return new ChainingProcessResult(stringBuilder, true, ruleList, goal);
        }
        do {
            iterationNumber++;
            stringBuilder.append("  ITERATION " + iterationNumber).append(NL);
            facts_changed = false;
            for (Rule rule : getRules()) {
                stringBuilder.append("    " + rule.toString());
                if (!rule.getFlag1() && !rule.getFlag2()) {
                    //Checks if facts already contains current rule antecedents (rule conditions)
                    if (getFacts().containsAll(rule.getAntecedents())) {
                        //Checks if facts doesn't contain consequent (rule result)
                        if (!getFacts().contains(rule.getConsequent())) {
                            rule.setFlag1(true);
                            ruleList.add(rule);
                            getFacts().add(rule.getConsequent());
                            stringBuilder.append(" apply. Raise flag1. Facts " + listFacts() + ".").append(NL);
                            facts_changed = true;
                            if (rule.getConsequent().equals(getGoal())) {
                                stringBuilder.append("    Goal achieved.").append(NL).append(NL);
                                return new ChainingProcessResult(getSb(), true, ruleList, getGoal());
                            }
                        }
                        else{
                            rule.setFlag2(true);
                            stringBuilder.append(" not applied, because RHS in facts. Raise flag2.").append(NL);
                        }
                    } else {
                        getSb().append(" not applied, because of lacking ");
                        for(String antecedent: rule.getAntecedents()){
                            if (!getFacts().contains(antecedent)) {
                                getSb().append(antecedent);
                                break;
                            }
                        }
                        getSb().append(".").append(NL);
                    }
                } else if (rule.getFlag2()){
                    getSb().append(" skip, because flag2 raised.").append(NL);
                } else {
                    getSb().append(" skip, because flag1 raised.").append(NL);
                }
                if (facts_changed) {
                    break;
                }
            }
            getSb().append(NL);
        } while (facts_changed);
        return new ChainingProcessResult(getSb(), false, ruleList, getGoal());
    }
}
