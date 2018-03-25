package data.dao;

import data.entity.Rule;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class RuleDAO {
    @Inject
    private EntityManager manager;

    public void create(Rule rule) {
        manager.persist(rule);
    }

    public void deleteById(Long id){
        manager.createNamedQuery("Rule.deleteById").setParameter("id", id).executeUpdate();
    }

    public List<Rule> getAll(){
        List<Rule> rules = manager.createNamedQuery("Rule.findAll", Rule.class).getResultList();
        return rules;
    }

    public Rule findById(Long id){
        return manager.createNamedQuery("Rule.findById", Rule.class).setParameter("id", id).getSingleResult();
    }
    public List<Rule> findByConsequent(String consequent){
        return manager.createNamedQuery("Rule.findByConsequent", Rule.class).setParameter("consequent", consequent).getResultList();
    }
}
