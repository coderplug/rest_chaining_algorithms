package main;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    private String chainingType;
    private String goal;

    //@XmlElementWrapper(name = "rules")
    //@XmlElement(name = "rule")
    @XmlTransient
    private List<Rule> rules;

    @XmlElementWrapper(name = "facts")
    @XmlElement(name = "fact")
    private List<String> facts;

    public Data(){

    }

    public Data(String goal, List<Rule> rules, List<String> facts, String chainingType) {
        this.goal = goal;
        this.rules = rules;
        this.facts = facts;
        this.chainingType = chainingType;
    }

    @XmlTransient
    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public List<String> getFacts() {
        return facts;
    }

    public void setFacts(List<String> facts) {
        this.facts = facts;
    }

    public String getGoal() {

        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getChainingType() {
        return chainingType;
    }

    public void setChainingType(String chainingType) {
        this.chainingType = chainingType;
    }

    @Override
    public String toString()
    {
        String NL = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("  1) Rules").append(NL);
        for(Rule r: rules){
            stringBuilder.append("     ").append(r.toString()).append(NL);
        }
        stringBuilder.append(NL);
        stringBuilder.append("  2) Facts").append(NL)
                     .append("     ").append(listFacts()).append(NL).append(NL)
                     .append("  3) Goal").append(NL)
                     .append("     ").append(goal);
        return stringBuilder.toString();
    }

    //Used for listing facts in result string
    public String listFacts() {
        StringBuilder result = new StringBuilder();
        for(int i=0; i<facts.size(); i++)
        {
            if (i != facts.size() - 1)
            {
                result.append(facts.get(i) + ", ");
            }
            else
            {
                result.append(facts.get(i));
            }
        }
        return result.toString();
    }
}
