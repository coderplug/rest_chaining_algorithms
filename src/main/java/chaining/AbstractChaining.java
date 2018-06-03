package chaining;


import data.Data;
import data.Result;
import data.Trace;

//JAXB importavimai
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.LinkedList;
import java.util.List;


@XmlRootElement(name = "chainingQuery", namespace="")
public abstract class AbstractChaining {

    //Kuriant XML atvaizdavimą šis laukas nepridedamas į XML dokuementą
    @XmlTransient
    //Laukas, nurodo naujos eilutės simbolį
    protected static String NL = System.getProperty("line.separator");

    //Kuriant XML atvaizdavimą šis laukas nepridedamas į XML dokuementą
    @XmlTransient
    private List<String> facts;

    private Data data;
    private Trace trace;
    private Result result;

    public AbstractChaining(){
        this.trace = new Trace(new LinkedList<String>());
    }

    public AbstractChaining(Data data) {
        this.data = data;
        this.trace = new Trace(new LinkedList<String>());
        facts = new LinkedList<>(data.getFacts());
    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Trace getTrace() {
        return trace;
    }

    public void setTrace(Trace trace) {
        this.trace = trace;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @XmlTransient
    public List<String> getFacts() {
        return facts;
    }

    public void setFacts(List<String> facts) {
        this.facts = facts;
    }

    public abstract void execute();

    //Faktų sąrašo atvaizdavimas String pavidalu
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

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("PART 1. Data").append(NL).append(NL);
        stringBuilder.append(data).append(NL).append(NL);
        stringBuilder.append("PART 2. Trace").append(NL).append(NL);
        stringBuilder.append(trace).append(NL).append(NL);
        stringBuilder.append("PART 3. Results").append(NL).append(NL);
        stringBuilder.append(result);

        return stringBuilder.toString();
    }
}
