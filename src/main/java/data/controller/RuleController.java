package data.controller;


import data.dao.RuleDAO;
import data.entity.Rule;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class RuleController {

    @Inject
    private RuleDAO ruleDAO;

    private List<Rule> rulesList;

    @PostConstruct
    private void init(){
        rulesList = ruleDAO.getAll();
    }

    public List<Rule> getRulesList(){
        return ruleDAO.getAll();
    }

    @Override
    public String toString(){
        rulesList = ruleDAO.getAll();
        StringBuilder builder = new StringBuilder();
        for (Rule rule: rulesList)
        {
            builder.append(rule.toString());
        }
        return builder.toString();
    }
}
