import chaining.AbstractChaining;
import chaining.BackwardChaining;
import chaining.ForwardChaining;
import data.Data;
import data.Trace;

//CDI importuojama anotacija
import javax.inject.Inject;

//JAX-RS importavimai
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Nurodomas serviso URI
@Path("/rest")
//Web serviso resursų klasė
public class Resources {
    private String NL = System.getProperty("line.separator");

    //Dependency Injection, pasirūpinama šio objekto sukūrimu
    @Inject
    //Pradiniai duomenys
    private Data data;

    //Išvedimo algoritmo objekto sukūrimas
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

    //pavyzdinė nuoroda - get/forward/A/B
    //Metodas apdoroja HTTP GET užklausas
    @GET
    //Resurso URI su 3 parametrais
    @Path("/get/{chaining}/{goal}/{fact}")
    //Metodas sukurs XML tipo rezultatą
    @Produces({MediaType.APPLICATION_XML})
    public AbstractChaining getChainingExample(@PathParam("chaining") String chainingType,
                                               @PathParam("goal") String goal,
                                               @PathParam("fact") String fact) {
        //Perdavus neteisingą išvedimo pavadinimą - grąžinamas tuščias rezultatas
        if(!chainingType.equals("forward") && !chainingType.equals("backward")){
            return new BackwardChaining();
        }
        List facts = new ArrayList<>();

        //Užpildomi parametrais gauti duomenys
        facts.add(fact);
        data.setChainingType(chainingType);
        data.setFacts(facts);
        data.setGoal(goal);

        //Pridedamos visos esamos duomenų bazės
        List<String> databases = new LinkedList<>();
        databases.add("local1");
        databases.add("local2");
        databases.add("remote1");
        databases.add("remote2");
        data.setDatabases(databases);

        //Sukuriamas išvedimo algoritmas pagal turimus duomenis
        AbstractChaining chainingAlgorithm = createChaining(data);

        //Vykdomas išvedimas
        chainingAlgorithm.execute();

        //Grąžiname įvykdytos užklausos rezultatus
        return chainingAlgorithm;
    }

    //Metodas apdoroja HTTP POST užklausas
    @POST
    //Resurso URI
    @Path("/post/trace")
    //Metodas priima XML media tipo failą
    @Consumes({MediaType.APPLICATION_XML})
    //Metodas sukurs XML tipo rezultatą
    @Produces({MediaType.APPLICATION_XML})
    //Priima XML dokumentą iš POST užklausos,
    // kuris yra sukuriamas kaip Data objektas
    // naudojamas užklausai vykdyti
    // ir grąžina Trace objektą XML formatu
    public Trace postTrace(Data receivedData) {
        //Tikrinama ar gauti duomenys nėra tušti
        if (receivedData == null || receivedData.getChainingType() == null || receivedData.getChainingType().isEmpty()){
            return new Trace();
        }

        //Gauti data objekto laukai perkopijuojami į jau egzistuojantį Data objektą
        cloneDataObject(receivedData);

        //Sukuriamas išvedimo algoritmas pagal turimus duomenis
        AbstractChaining chainingAlgorithm = createChaining(data);


        //Vykdomas išvedimas
        chainingAlgorithm.execute();

        //Grąžiname įvykdytos užklausos rezultatus
        return chainingAlgorithm.getTrace();
    }

    //Metodas apdoroja HTTP POST užklausas
    @POST
    //Resurso URI
    @Path("/post/chaining")
    //Metodas priima XML media tipo failą
    @Consumes({MediaType.APPLICATION_XML})
    //Metodas sukurs XML tipo rezultatą
    @Produces({MediaType.APPLICATION_XML})
    //Priima XML dokumentą iš POST užklausos,
    // kuris yra sukuriamas kaip Data objektas
    // naudojamas užklausai vykdyti
    // ir grąžina vykdytos užklausos rezultatą XML formatu
    public AbstractChaining postAbstractChaining(Data receivedData) {
        //Tikrinama ar gauti duomenys nėra tušti
        if (receivedData == null || receivedData.getChainingType() == null || receivedData.getChainingType().isEmpty()){
            return new BackwardChaining();
        }
        //Gauti data objekto laukai perkopijuojami į jau egzistuojantį Data objektą
        cloneDataObject(receivedData);

        //Sukuriamas išvedimo algoritmas pagal turimus duomenis
        AbstractChaining chainingAlgorithm = createChaining(data);


        //Vykdomas išvedimas
        chainingAlgorithm.execute();

        //Grąžiname įvykdytos užklausos rezultatus
        return chainingAlgorithm;
    }

    //Gauto Data objekto laukų perkopijavimas
    private void cloneDataObject(Data receivedData){
        data.setGoal(receivedData.getGoal());
        data.setFacts(receivedData.getFacts());
        data.setChainingType(receivedData.getChainingType());
        data.setDatabases(receivedData.getDatabases());
    }
}
