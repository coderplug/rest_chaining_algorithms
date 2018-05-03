package data.controller;


import data.dao.RuleDAO;
import data.entity.Rule;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;

@Named
@ApplicationScoped
public class RuleController {

    @Inject
    private RuleDAO ruleDAO;

    private List<Rule> rulesList;

    @PostConstruct
    private void init(){
        rulesList = getRulesList();
    }

    public List<Rule> getRulesList(){
        return ruleDAO.getAll();
    }

    public List<Rule> getRulesList(List<String> servers){
        List<Rule> ruleList = new LinkedList<>();
        for(String server: servers)
        {
            ruleList.addAll(ruleDAO.findByServer(server));
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
