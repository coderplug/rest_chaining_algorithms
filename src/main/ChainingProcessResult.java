package main;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChainingProcessResult {

    protected static String NL = System.getProperty("line.separator");
    private final String executionSteps;
    private final Boolean goalReached;
    private List<Rule> ruleSequence;

    public ChainingProcessResult(StringBuilder sb, Boolean goalReached, List<Rule> ruleSequence, String goal){
        super();
        this.goalReached = goalReached;
        this.ruleSequence = ruleSequence;
        this.executionSteps = formatAnswer(sb, goal);
    };

    private String formatAnswer(StringBuilder sb, String goal) {
        sb.append("PART 3. Results").append(NL);
        if (goalReached && ruleSequence.size() == 0)
        {
            sb.append("  Goal " + goal + " among facts. Empty path.");
        }
        else if (!goalReached)
        {
            sb.append("  Goal " + goal + " was not reached.");
        }
        else
        {
            sb.append("  1) Goal " + goal + " achieved.").append(NL);
            sb.append("  2) Path: " + getRuleSequenceString() + ".");
        }
        return sb.toString();
    }

    public String getExecutionSteps() {
        return executionSteps;
    }

    public Boolean getGoalReached() {
        return goalReached;
    }

    public List<Rule> getRuleSequence() {
        return ruleSequence;
    }
    public String getRuleSequenceString(){
        StringBuilder stringBuilder = new StringBuilder();
        String delim = "";
        for (Rule rule : ruleSequence) {
            stringBuilder.append(delim).append("R" + rule.getNumber());
            delim = ", ";
        }
        if(ruleSequence.size() == 0 ){
            stringBuilder.append("empty");
        }
        return stringBuilder.toString();
    }
}
