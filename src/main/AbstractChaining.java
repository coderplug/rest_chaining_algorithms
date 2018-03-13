package main;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractChaining {

    protected static String NL = System.getProperty("line.separator");

    private String goal;
    private List<Rule> rules;
    private List<String> facts;
    private StringBuilder sb;


    public AbstractChaining(String goal, List<Rule> rules,
                            List<String> facts, StringBuilder sb) {
        super();
        this.goal = goal;
        this.rules = new ArrayList<Rule>(rules);
        this.facts = new ArrayList<String>(facts);
        this.sb = sb;
    }

    public String getGoal() {
        return goal;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public List<String> getFacts() {
        return facts;
    }

    public StringBuilder getSb() {
        return sb;
    }

    public ChainingProcessResult execute() {
        this.sb.append("PART 1. Data").append(NL).append(NL)
                   .append("  1) Rules").append(NL);
        for(Rule r: rules){
            this.sb.append("     ").append(r.toString()).append(NL);
        }
        sb.append(NL);
        sb.append("  2) Facts").append(NL)
                .append("     ").append(listFacts()).append(NL).append(NL)
                .append("  3) Goal").append(NL)
                .append("     ").append(goal).append(NL).append(NL);
        sb.append("PART 2. Trace").append(NL).append(NL);
        ChainingProcessResult result = doExecute();
        return result;
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

    //Used for listing risk conditions in result string
    public String listAntecendants(Rule rule) {
        StringBuilder result = new StringBuilder();
        List<String> antecendants = rule.getAntecedents();
        for(int i=0; i<antecendants.size(); i++)
        {
            if (i != antecendants.size() - 1)
            {
                result.append(antecendants.get(i) + ", ");
            }
            else
            {
                result.append(antecendants.get(i));
            }
        }
        return result.toString();
    }

    protected abstract ChainingProcessResult doExecute();
}
