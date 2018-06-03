package data;

import data.controller.RuleController;
import data.entity.Rule;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;


//Naudojama surišti objektą su XML elementais
@XmlRootElement
//Nurodoma kaip kuriamas XML failas (pagal laukus), naudojama sąrašų elementams atvaizduoti
@XmlAccessorType(XmlAccessType.FIELD)
//CDI komponentas
@Named
//Objektas galioja tik kol vykdoma užklausa
@RequestScoped
//Klasė, aprašanti pradinius duomenis
public class Data {

    //Sąrašo tėvinis elementas
    @XmlElementWrapper(name = "databases")
    //Sąrašo vaikinis elementas
    @XmlElement(name = "database")
    //Duomenų bazių sąrašas
    private List<String> databases;

    //Išvedimo tipo pavadinimas ("forward" arba "backward")
    private String chainingType;

    //Užklausos tikslas
    private String goal;

    //Neatvaizduojamas XML dokumente
    @XmlTransient
    //Pasirūpinama, kad prireikus būtų sukurtas objektas
    @Inject
    //Taisyklių valdiklis
    private RuleController ruleController;

    @XmlElementWrapper(name = "rules")
    @XmlElement(name = "rule")
    private List<Rule> rules;

    @XmlElementWrapper(name = "facts")
    @XmlElement(name = "fact")
    private List<String> facts;

    //XML generavimui reikalingas be parametrų konstruktorius
    public Data(){
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
        rules = ruleController.getRulesList(databases);
        return rules;
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
        List<Rule> rules = getRules();
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

    //Taisyklių atvaizdavimas tekstu
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
