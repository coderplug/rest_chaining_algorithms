package data;

import data.entity.Rule;

//JAXB importavimas
import javax.xml.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

//Naudojama surišti objektą su XML elementais
@XmlRootElement
//Nurodoma kaip kuriamas XML failas (pagal laukus), naudojama sąrašų elementams atvaizduoti
@XmlAccessorType(XmlAccessType.FIELD)
//Algoritmo užklausos rezultatai
public class Result {

    //Nurodoma ar rastas kelias
    private Boolean goalReached;

    //Sąrašą aprašantis XML elementas
    @XmlElementWrapper(name = "rule_sequence")
    //Sąrašo dalies XML elementas
    @XmlElement(name = "rule")
    //Taisyklių seka
    private List<Rule> ruleSequence;

    //Ignoruojamas laukas, kuriant XML failą
    @XmlTransient
    //Algoritmo metu modifikuotas Data objektas
    private Data data;

    //XML generavimui reikalingas be parametrų konstruktorius
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

    //Taisyklių sekos atvaizdavimas tekstu
    public String getRuleSequenceString(){
        StringBuilder stringBuilder = new StringBuilder();
        String delim = "";
        for (Rule rule : ruleSequence) {
            stringBuilder.append(delim).append(rule.getServer()).append(".R" + rule.getId());
            delim = ", ";
        }
        if(ruleSequence.size() == 0 ){
            stringBuilder.append("empty");
        }
        return stringBuilder.toString();
    }
}
