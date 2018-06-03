package data.controller;


import data.dao.RuleDAO;
import data.entity.Rule;

//CDI importavimai
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.LinkedList;
import java.util.List;

//CDI komponentas
@Named
//Galioja visos aplikacijos metu
@ApplicationScoped
public class RuleController {

    //Esant poreikiui sukuriamas objektas ir įterpiamas
    @Inject
    //Susisiekimas su taisyklėmis duomenų bazėje
    private RuleDAO ruleDAO;

    //Taisyklių sąrašas
    private List<Rule> rulesList;

    //Kviečiamas sukūrus komponentą
    @PostConstruct
    private void init(){
        rulesList = getRulesList();
    }

    public List<Rule> getRulesList(){
        return ruleDAO.getAll();
    }

    //Taisyklių sąrašo surinkimas iš serverių
    public List<Rule> getRulesList(List<String> servers){
        List<Rule> ruleList = new LinkedList<>();
        for(String server: servers)
        {
            ruleList.addAll(ruleDAO.findByServer(server));
        }
        for(Rule rule: ruleList){
            rule.orderAntecedents();
        }
        return ruleList;
    }

    @Override
    public String toString(){
        rulesList = getRulesList();
        StringBuilder builder = new StringBuilder();
        for (Rule rule: rulesList)
        {
            builder.append(rule.toString());
        }
        return builder.toString();
    }
}
