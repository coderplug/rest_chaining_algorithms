package main.data;

import javax.xml.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Trace {

    @XmlElementWrapper(name = "trace_list")
    @XmlElement(name = "line")
    private List<String> traceList;

    @XmlTransient
    private List<String> strings;
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
        this.strings = new LinkedList<>();
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
