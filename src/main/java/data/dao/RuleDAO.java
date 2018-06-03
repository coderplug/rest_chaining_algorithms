package data.dao;

import data.entity.Rule;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

//Aplikacijos metu gyvuojantis objektas
@ApplicationScoped
//Susisiekimas su taisyklėmis, laikomomis duomenų bazėse
public class RuleDAO {

    //Esant poreikiui sukuriamas
    @Inject
    //Esybių valdymo objektas
    private EntityManager manager;

    //Surenkamos visos taisyklės
    public List<Rule> getAll(){
        List<Rule> rules = manager.createNamedQuery("Rule.findAll", Rule.class).getResultList();
        return rules;
    }

    //Surenkamos taisyklės pagal id
    public Rule findById(Long id){
        return manager.createNamedQuery("Rule.findById", Rule.class).setParameter("id", id).getSingleResult();
    }

    //Surenkamos taisyklės pagal jų rezultatą
    public List<Rule> findByConsequent(String consequent){
        return manager.createNamedQuery("Rule.findByConsequent", Rule.class).setParameter("consequent", consequent).getResultList();
    }

    //Surenkamos taisyklės pagal serverį
    public List<Rule> findByServer(String server){
        return manager.createNamedQuery("Rule.findByServer", Rule.class).setParameter("server", server).getResultList();
    }
}
