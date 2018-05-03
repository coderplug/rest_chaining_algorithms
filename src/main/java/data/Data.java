package data;

import data.controller.RuleController;
import data.entity.Rule;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Named
@RequestScoped
public class Data {

    @XmlElementWrapper(name = "databases") //List parent node
    @XmlElement(name = "database") //List children node
    private List<String> databases;

    private String chainingType;
    private String goal;

    @XmlTransient
    @Inject
    private RuleController ruleController;

    @XmlElementWrapper(name = "facts")
    @XmlElement(name = "fact")
    private List<String> facts;

    //@Inject
    public Data(){
        //rules = ruleDAO.getAll();
        databases = new LinkedList<>();
        facts = new LinkedList<>();
    }

    public Data(String goal, List<String> facts, String chainingType) {
        this.goal = goal;
        this.facts = facts;
        this.chainingType = chainingType;
    }

    public List<String> getDatabases() {
        return databases;
    }

    public void setDatabases(List<String> databases) {
        this.databases = databases;
    }

    public List<Rule> getRules() {
        return ruleController.getRulesList(databases);
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
        List<Rule> rules = getRules();//ruleDAO.getAll();
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
