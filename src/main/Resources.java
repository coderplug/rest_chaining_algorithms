package main;

import main.chaining.AbstractChaining;
import main.chaining.BackwardChaining;
import main.chaining.ForwardChaining;
import main.data.Data;
import main.data.Trace;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/rest")
public class Resources {
    String NL = System.getProperty("line.separator");

    private static AbstractChaining createChaining(Data data)
    {
        if (data != null && data.getChainingType() != null) {
            switch (data.getChainingType().toLowerCase()) {
                case "backward":
                    return new BackwardChaining(data);
                case "forward":
                    return new ForwardChaining(data);
                default:
                    return null;
            }
        }
        return null;
    }

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
        Data data = new Data(goal, facts, chainingType);
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
    public Trace postTrace(Data data) {
        if (data == null || data.getChainingType() == null || data.getChainingType().isEmpty()){
            return new Trace();
        }
        //IOReader reader = new IOReader();
        //data.setRules(reader.rulesFromFile());
        //AbstractChaining chainingAlgorithm = createChaining(data);
        //chainingAlgorithm.execute();
        return null;//chainingAlgorithm.getTrace();
    }

    // The Java method will process HTTP POST requests
    @POST
    @Path("/post/chaining")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    //Gets XML from POST request, parses to Data object, using which a result is generated
    public AbstractChaining postAbstractChaining(Data data) {
        if (data == null || data.getChainingType() == null || data.getChainingType().isEmpty()){
            return new BackwardChaining();
        }
        //IOReader reader = new IOReader();
        //data.setRules(reader.rulesFromFile());
        //AbstractChaining chainingAlgorithm = createChaining(data);
        //chainingAlgorithm.execute();
        //return chainingAlgorithm;
        return null;
    }
}
