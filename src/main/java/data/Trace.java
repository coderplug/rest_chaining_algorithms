package data;

//JAXB anotacijų importavimas
import javax.xml.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

//Naudojama surišti objektą su XML elementais
@XmlRootElement
//Nurodoma kaip kuriamas XML failas (pagal laukus), naudojama sąrašų elementams atvaizduoti
@XmlAccessorType(XmlAccessType.FIELD)
//Registruojami algoritmų žingsniai
public class Trace {

    //Sąrašą aprašantis XML elementas
    @XmlElementWrapper(name = "trace_list")
    //Sąrašo dalies XML elememntas
    @XmlElement(name = "line")
    //Žinsnių sąrašas
    private List<String> traceList;

    //XML generavimui reikalingas be parametrų konstruktorius
    public Trace (){
        traceList = new LinkedList<>();
    }

    public void addToTrace(String line){
        if (traceList == null)
        {
            traceList = new LinkedList<>();
        }
        traceList.add(line);
    }

    public Trace(List<String> traceList) {
        this.traceList = traceList;
    }

    public List<String> getTraceList() {
        return traceList;
    }

    public void setTraceList(List<String> traceList) {
        this.traceList = traceList;
    }

    @Override
    public String toString() {
        String NL = System.getProperty("line.separator");
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(String line: traceList)
        {
            if (count != 0)
            {
                stringBuilder.append(NL);
            }
            stringBuilder.append(line);
            count++;
        }
        return stringBuilder.toString();
    }
}
