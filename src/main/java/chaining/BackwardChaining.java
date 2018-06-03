package chaining;

import data.Data;
import data.Result;
import data.Trace;
import data.entity.Antecedent;
import data.entity.Rule;
//JAXB importavimai
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "chainingQuery") //XML šakninis elementas
public class BackwardChaining extends AbstractChaining { //Klasė aprašo ir vykdo atbulinį išvedimą

    private List<String> goals;     //Užklausos vykdymo metu kaupiami tikslai

    private List<Rule> resultPath;  //Užklausos metu keičiamas taisyklių kelias

    private int recursionLevel;     //Rekursyvumo lygis, naudojamas užklausos vykdymo sekai aprašyti

    private int lineCount;          //Iteracijos numeris

    public BackwardChaining() {
        super();
        resultPath = new LinkedList<>();
        goals = new LinkedList<>();
        lineCount = 0;
        recursionLevel = 0;
    }

    public BackwardChaining(Data data) {
        super(data);
        resultPath = new LinkedList<>();
        goals = new LinkedList<>();
        lineCount = 0;
        recursionLevel = 0;
    }

    public void execute(){ //Kviečimas algoritmas ir nustatomas rezultatas
        boolean success = backwardChaining(getData().getGoal());
        setResult(new Result(success, resultPath, getData()));
    }

    private boolean backwardChaining(String currentGoal) {  //1. Algoritmo realizacija, rekursyvi funkcija
        Trace trace = getTrace();
        List<Rule> rules = getData().getRules();
        List<String> facts = getFacts();
        recursionLevel++;

        //2. Jei faktuose yra tikslas, grąžinam, jog esamas (nebūtinai pradinis) tikslas rastas
        if (facts.contains(currentGoal)) {
            trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal + "." + " Fact.");
            recursionLevel--;
            return true; //3.
        }

        goals.add(currentGoal); //4. Pridedame esamą tikslą prie užklausos vykdymo metu kaupiamų tikslų sąrašo


        for (Rule rule : rules) { //5. Iteruojamos taisyklės


            /* 6. Tikrinama ar :
             *      1. taisyklė nepanaudota (flag1 yra false)
             *      ir
             *      2. esamas tikslas yra taisyklės rezultatas
            */
            if (!rule.getFlag1() && rule.getConsequent().equals(currentGoal)) {
                List<Antecedent> antecedents = rule.getAntecedents();

                //7. Kintamasis naudojamas patikrinti ar nėra ciklų ir neišvedamų dalių
                boolean pathExists = true;

                trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal + "."
                        + " Take " + rule.toString() + "."
                        + " New goals " + rule.listAntecedents() + ".");

                for (Antecedent antecedent : antecedents) { //8. Iteruojamos taisyklės sąlygos (antecedentai)

                    if (!goals.contains(antecedent.getName())){ //9. Tikrinama ar tiksluose nėra taisyklės sąlygos (ciklo)

                        //10. Jei backwardChaining funkcija yra nesėkminga - esamas tisklas neišsiveda
                        if (!backwardChaining(antecedent.getName())) {
                            pathExists = false;     //11.
                            break;   //12.
                        }
                    } else {   //13.
                        recursionLevel++;
                        trace.addToTrace(getRecursionLevel() + "Goal " + antecedent.getName() + ". Loop.");
                        recursionLevel--;
                        pathExists = false;   //14.
                        break;   //15.
                    }
                }

                if (pathExists) {  //16. Tikrinama ar išvedamas kelias

                    rule.setFlag1(true);  //17. Taisyklė panaudojama

                    facts.add(rule.getConsequent());   //18. Taisyklė pridedama prie pildomų faktų sąrašo
                    resultPath.add(rule);   //19. Taisyklė pridedama prie taisyklių iki tikslo sekos
                    trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal
                            + ". New fact: " + rule.getConsequent()
                            + ". Facts: " + listFacts() + ".");
                    recursionLevel--;
                    goals.remove(currentGoal);   //20. Tikslas panaikinamas, nes buvo rastas
                    return true;   //21.
                }
            }
        }
        goals.remove(currentGoal);  //22. Naikinamas tikslas, nes nepavyko rasti kelio iki esamo tikslo
        /*
         * Iteruojamos taisyklės ir tikrinama ar jos panaudotos (išvedamos iš faktų) šiame kelyje ir jos:
         * - atžymimas flag1
         * - taisyklė pašalinama iš kelio, nes nėra reikalinga išvedimui
         * - taisyklės rezultatas pašalinamas iš pildomų faktų sąrašo
         */
        for(Rule rule: rules){  //23.
            if(rule.getFlag1()){  //24.
                rule.setFlag1(false);  //25.
                resultPath.remove(rule);  //26.
                facts.remove(rule.getConsequent());  //27.
            }
        }
        trace.addToTrace(getRecursionLevel() + "Goal " + currentGoal + "."
                + " Fact cannot be derived" + ".");
        recursionLevel--;
        return false;   //28.
    }


    @XmlTransient  //Nurodoma, jog recursionLevel neįeina į XML dokumentą
    private String getRecursionLevel() {  //recursionLevel atvaizdavimas String formatu
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
