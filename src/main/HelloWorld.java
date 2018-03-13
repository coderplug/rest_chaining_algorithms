package main;

import main.chaining.AbstractChaining;
import main.chaining.BackwardChaining;
import main.chaining.ForwardChaining;
import main.data.Data;
import main.data.Trace;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/rest")
public class HelloWorld {
    String NL = System.getProperty("line.separator");

    public static AbstractChaining createChaining(Data data)
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
    @Path("/get/{username}")
    // The Java method will produce content identified by the MIME Media type "text/plain"
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_XML})
    //@Produces("text/plain")
    public AbstractChaining getClichedMessage(@PathParam("username") String userName) {
        IOReader io = new IOReader();
        String chainingType = "backward";
        AbstractChaining chainingAlgorithm = io.readFromFile(chainingType);
        /*return new Data(chainingAlgorithm.getGoal(),
                        chainingAlgorithm.getRules(),
                        chainingAlgorithm.getFacts(),
                        chainingType);*/
        /*List<Rule> rules;
        List<String> antecedents;
        Rule rule;
        List<String> facts;

        rules = new ArrayList<>();

        antecedents = new ArrayList<>();
        antecedents.add("B");
        rule = new Rule("A", antecedents, 1);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("C");
        rule = new Rule("A", antecedents, 2);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("D");
        rule = new Rule("C", antecedents, 3);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("E");
        rule = new Rule("D", antecedents, 4);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("E");
        rule = new Rule("B", antecedents, 5);
        rules.add(rule);

        facts = new ArrayList<>();

        facts.add("E");
        facts.add("JJ");

        AbstractChaining chainingAlgorithm = new BackwardChaining("A", rules, facts, new StringBuilder());
        */
        //Result result = chainingAlgorithm.execute();
        chainingAlgorithm.execute();
        // Return some cliched textual content
        //return "Hello World";
        /*io.writeToFile(chainingAlgorithm.getExecutionSteps());
        return userName + NL + result.getExecutionSteps();    */
        io.writeToFile(chainingAlgorithm.toString());
        return chainingAlgorithm;//chainingAlgorithm.getTrace();
        //return userName + NL + result.getExecutionSteps();
    }

    // The Java method will process HTTP GET requests
    @POST
    @Path("/post1")
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Consumes({MediaType.APPLICATION_XML})
    //@Produces({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public Data getClichedMessage3(Data data) {
        return data;
    }

    @POST
    @Path("/post2")
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Consumes({MediaType.APPLICATION_XML})
    //@Produces({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public Trace getClichedMessage(Data data) {
        if (data == null || data.getChainingType() == null || data.getChainingType().isEmpty()){
            return new Trace();
        }
        IOReader reader = new IOReader();
        data.setRules(reader.rulesFromFile());
        AbstractChaining chainingAlgorithm = createChaining(data);
        chainingAlgorithm.execute();
        return chainingAlgorithm.getTrace();
    }

    // The Java method will process HTTP GET requests
    @POST
    @Path("/post")
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Consumes({MediaType.APPLICATION_XML})
    //@Produces({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public AbstractChaining getClichedMessage2(Data data) {
        if (data == null || data.getChainingType() == null || data.getChainingType().isEmpty()){
            return new BackwardChaining();
        }
        IOReader reader = new IOReader();
        data.setRules(reader.rulesFromFile());
        AbstractChaining chainingAlgorithm = createChaining(data);
        chainingAlgorithm.execute();
        return chainingAlgorithm;
        /*IOReader io = new IOReader();
        String chainingType = "backward";
        AbstractChaining chainingAlgorithm = io.readFromFile(chainingType);
        *//*return new Data(chainingAlgorithm.getGoal(),
                        chainingAlgorithm.getRules(),
                        chainingAlgorithm.getFacts(),
                        chainingType);*//*
        *//*List<Rule> rules;
        List<String> antecedents;
        Rule rule;
        List<String> facts;

        rules = new ArrayList<>();

        antecedents = new ArrayList<>();
        antecedents.add("B");
        rule = new Rule("A", antecedents, 1);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("C");
        rule = new Rule("A", antecedents, 2);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("D");
        rule = new Rule("C", antecedents, 3);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("E");
        rule = new Rule("D", antecedents, 4);
        rules.add(rule);

        antecedents = new ArrayList<>();
        antecedents.add("E");
        rule = new Rule("B", antecedents, 5);
        rules.add(rule);

        facts = new ArrayList<>();

        facts.add("E");
        facts.add("JJ");

        AbstractChaining chainingAlgorithm = new BackwardChaining("A", rules, facts, new StringBuilder());
        *//*
        //Result result = chainingAlgorithm.execute();
        chainingAlgorithm.execute();
        // Return some cliched textual content
        //return "Hello World";
        *//*io.writeToFile(chainingAlgorithm.getExecutionSteps());
        return userName + NL + result.getExecutionSteps();    *//*
        io.writeToFile(chainingAlgorithm.toString());
        return chainingAlgorithm;*///chainingAlgorithm.getTrace();
        //return userName + NL + result.getExecutionSteps();
    }
}
