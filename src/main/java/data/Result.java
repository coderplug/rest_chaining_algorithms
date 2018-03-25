package data;

import data.entity.Rule;

import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //Needed to show List values correctly
public class Result {
    private Boolean goalReached;

    @XmlElementWrapper(name = "rule_sequence")
    @XmlElement(name = "rule")
    private List<Rule> ruleSequence;

    @XmlTransient  //Used to avoid when creating xml file
    private Data data;

    public Result(){
        ruleSequence = new LinkedList<>();
    }

    public Result(Boolean goalReached, List<Rule> ruleSequence, Data data){
        this.goalReached = goalReached;
        this.ruleSequence = ruleSequence;
        this.data = data;
    }

    @Override
    public String toString() {
        String NL = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append("PART 3. Results").append(NL);
        if (goalReached && ruleSequence.size() == 0)
        {
            stringBuilder.append("  Goal " + data.getGoal() + " among facts. Empty path.");
        }
        else if (!goalReached)
        {
            stringBuilder.append("  Goal " + data.getGoal() + " was not reached.");
        }
        else
        {
            stringBuilder.append("  1) Goal " + data.getGoal() + " achieved.").append(NL);
            stringBuilder.append("  2) Path: " + getRuleSequenceString() + ".");
        }
        return stringBuilder.toString();
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
            stringBuilder.append(delim).append("R" + rule.getId());
            delim = ", ";
        }
        if(ruleSequence.size() == 0 ){
            stringBuilder.append("empty");
        }
        return stringBuilder.toString();
    }
}
