import chaining.AbstractChaining;
import chaining.BackwardChaining;
import chaining.ForwardChaining;
import data.Data;
import data.Trace;
import data.entity.Antecedent;
import data.entity.Rule;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Path("/rest")
public class Resources {
    private String NL = System.getProperty("line.separator");

    @Inject
    private Data data;

    private static AbstractChaining createChaining(Data receivedData)
    {
        if (receivedData != null && receivedData.getChainingType() != null) {
            switch (receivedData.getChainingType().toLowerCase()) {
                case "backward":
                    return new BackwardChaining(receivedData);
                case "forward":
                    return new ForwardChaining(receivedData);
                default:
                    return null;
            }
        }
        return null;
    }

    //get/forward/A/B
    // The Java method will process HTTP GET requests
    @GET
    @Path("/get/{chaining}/{goal}/{fact}")
    // The Java method will produce content in XML media type
    @Produces({MediaType.APPLICATION_XML})
    public AbstractChaining getChainingExample(@PathParam("chaining") String chainingType,
                                               @PathParam("goal") String goal,
                                               @PathParam("fact") String fact) {
        if(!chainingType.equals("forward") && !chainingType.equals("backward")){
            return new BackwardChaining();
        }
        List facts = new ArrayList<>();
        facts.add(fact);
        data.setChainingType(chainingType);
        data.setFacts(facts);
        data.setGoal(goal);
        AbstractChaining chainingAlgorithm = createChaining(data);
        chainingAlgorithm.execute();

        //IOReader io = new IOReader();
        //AbstractChaining chainingAlgorithm = io.readFromFile(chainingType);
        //chainingAlgorithm.execute();
        //io.writeToFile(chainingAlgorithm.toString());
        return chainingAlgorithm;
    }

    //POST request
    @POST
    @Path("/post/trace")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    //Gets XML from POST request, parses to Data object, using which a result is generated
    public Trace postTrace(Data receivedData) {
        if (receivedData == null || receivedData.getChainingType() == null || receivedData.getChainingType().isEmpty()){
            return new Trace();
        }
        //IOReader reader = new IOReader();
        cloneDataObject(receivedData);
        AbstractChaining chainingAlgorithm = createChaining(data);
        chainingAlgorithm.execute();
        return chainingAlgorithm.getTrace();
    }

    // The Java method will process HTTP POST requests
    @POST
    @Path("/post/chaining")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    //Gets XML from POST request, parses to Data object, using which a result is generated
    public AbstractChaining postAbstractChaining(Data receivedData) {
        if (receivedData == null || receivedData.getChainingType() == null || receivedData.getChainingType().isEmpty()){
            return new BackwardChaining();
        }
        cloneDataObject(receivedData);
        //IOReader reader = new IOReader();
        //data.setRules(reader.rulesFromFile());
        AbstractChaining chainingAlgorithm = createChaining(data);
        chainingAlgorithm.execute();
        return chainingAlgorithm;
        //return null;
    }
    @GET
    @Path("/aaa")
    @Produces({MediaType.TEXT_PLAIN})
    public String getRules()
    {
        StringBuilder builder = new StringBuilder();
        List<String> facts = new LinkedList<>();
        facts.add("B");
        for (Rule rule: data.getRules())
        {
            List<String> names = new ArrayList<>();
            for (String fact: facts)
            {
                builder.append(fact);
            }
            builder.append(" vs ");
            rule.orderAntecedents();
            for (Antecedent antecedent: rule.getAntecedents())
            {
                names.add(antecedent.getName());
                builder.append(antecedent.getName());
            }
            builder.append(" ").append(names.containsAll(facts)).append(NL);
        }
        return builder.toString();//data.toString();
    }

    private void cloneDataObject(Data receivedData){
        data.setGoal(receivedData.getGoal());
        data.setFacts(receivedData.getFacts());
        data.setChainingType(receivedData.getChainingType());
    }
}
