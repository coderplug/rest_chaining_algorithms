package chaining;

import data.Data;
import data.Result;
import data.Trace;
import data.entity.Antecedent;
import data.entity.Rule;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "chainingQuery") //XML šakninis elementas šiam objektui
public class ForwardChaining extends AbstractChaining { //Tiesioginio išvedimo algoritmo klasė

    public ForwardChaining(){
        super();
    }

    public ForwardChaining(Data data) {
        super(data);
    }

    public void execute() { //1. Tiesioginio išvedimo algoritmo vykdymas
        List<Rule> ruleList = new LinkedList<>();
        int iterationNumber = 0;

        boolean facts_changed; //Naudojamas patikrinti ar įvyko pakeitimų iteracijos metu

        Trace trace = getTrace();
        Data data = getData();
        String goal = data.getGoal();
        List<Rule> rules = data.getRules();
        List<String> facts = getFacts();

        if (facts.contains(goal)) { //2. Tikrinama ar pradiniuose faktuose yra tikslas
            setResult(new Result(true, ruleList, data)); //3.
            return; //4.
        }

        if (rules.size() != 0) { //5. Patikrinam ar tuščias siekiant išvengti papildomų įrašų protokole
            do { //6.
                iterationNumber++;
                trace.addToTrace("  ITERATION " + iterationNumber);

                facts_changed = false; //7.

                for (Rule rule : rules) { //8. Iteruojamos taisyklės
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("    " + rule.toString());

                    /* 9. Tikrina ar flag1 ir flag2 yra iškelti (angl. raised), kur:
                    *   flag1 - panaudota taisyklė
                    *   flag2 - nenaudojama, nes rezultatas jau yra kaupiamuose faktuose
                    */
                    if (!rule.getFlag1() && !rule.getFlag2()) {
                        List<String> antecedents = new LinkedList<>();
                        for (Antecedent antecedent : rule.getAntecedents()) {
                            antecedents.add(antecedent.getName());
                        }
                        //10. Patikrinama ar faktuose egzistuoja taisyklės antecedentai (sąlyginiai faktai)
                        if (facts.containsAll(antecedents)) {

                            //11. Tikrinama ar faktai neturi taisyklės rezultato (konsekvento)
                            if (!facts.contains(rule.getConsequent())) {
                                rule.setFlag1(true); //12. Naudojame taisyklę
                                ruleList.add(rule); //13.
                                facts.add(rule.getConsequent()); //14. Pridedamas rezultatas į faktus
                                trace.addToTrace(stringBuilder.toString() + " apply. Raise flag1. Facts "
                                        + listFacts() + ".");
                                facts_changed = true; //15.
                                //16. Tikrinama ar taisyklės rezultatas yra tikslas
                                if (rule.getConsequent().equals(goal)) {
                                    trace.addToTrace("    Goal achieved." + NL);
                                    //17. Kelias iki tikslo rastas, sukuriamas ir priskiriamas rezultato objektas
                                    setResult(new Result(true, ruleList, data));
                                    return; //18.
                                }
                            } else { //19.
                                //20. Taisyklės rezultatas egzistuoja faktuose, praleidžiam taisyklę
                                rule.setFlag2(true);
                                trace.addToTrace(stringBuilder.toString() + " not applied, because RHS in facts. Raise flag2.");
                            }
                        } else {
                            stringBuilder.append(" not applied, because of lacking ");
                            //Taisyklės vykdymui trūksta faktų, išvedame ko trūksta
                            for (Antecedent antecedent : rule.getAntecedents()) {
                                if (!getFacts().contains(antecedent.getName())) {
                                    stringBuilder.append(antecedent.getName());
                                    break;
                                }
                            }
                            trace.addToTrace(stringBuilder.toString() + ".");
                        }
                    } else if (rule.getFlag2()) {
                        //Pakeltas flag2, praleidžiam taisyklę
                        trace.addToTrace(stringBuilder.toString() + " skip, because flag2 raised.");
                    } else {
                        //Pakeltas flag1, praleidžiam taisyklę
                        trace.addToTrace(stringBuilder.toString() + " skip, because flag1 raised.");
                    }
                    if (facts_changed) { //21.
                        break; //22. Neįvyko pakeitimų, stabdomas algoritmas
                    }
                }
                trace.addToTrace("");
            } while (facts_changed); //23.
        }
        //Kelias nerastas, sukuriame rezultato objektą
        setResult(new Result(false, ruleList, data)); //24.
    }
}
