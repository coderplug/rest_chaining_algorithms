package data.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //Needed to show List values correctly
@NamedQueries({
        @NamedQuery(name = "Rule.findAll", query = "SELECT r FROM Rule r ORDER BY r.id ASC "),
        @NamedQuery(name = "Rule.findById", query = "SELECT r FROM Rule r WHERE r.id = :id ORDER BY r.id ASC "),
        @NamedQuery(name = "Rule.findByConsequent", query = "SELECT r FROM Rule r WHERE r.consequent = :consequent ORDER BY r.id ASC "),
        @NamedQuery(name = "Rule.findByServer", query = "SELECT r FROM Rule r WHERE r.server = :server ORDER BY r.id ASC")
})
@Entity
@Table(name = "dbs_rule", schema = "public", catalog = "chainingDB")
public class Rule {

    @Column(name = "consequent")
    private String consequent;

    @OneToMany(mappedBy = "assocRule", orphanRemoval=true)
    @XmlTransient
    private List<RuleAntecedent> ruleAntecedents;

    @XmlElementWrapper(name = "antecedents")
    @XmlElement(name = "antecedent")
    @Transient
    private List<Antecedent> antecedents;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "server")
    private String server;

    @XmlTransient
    @Transient
    private Boolean flag1;

    @XmlTransient
    @Transient
    private Boolean flag2;

    //Empty is needed to generate XML
    public Rule(){
        this.antecedents = new LinkedList<>();
        flag1 = false;
        flag2 = false;
    }

    public Rule(String consequent, List<Antecedent> antecedents, Long number) {
        this.consequent = consequent;
        this.antecedents = new LinkedList<>(antecedents);
        this.id = number;
        flag1 = false;
        flag2 = false;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public List<RuleAntecedent> getRuleAntecedents() {
        return ruleAntecedents;
    }

    public void setRuleAntecedents(List<RuleAntecedent> ruleAntecedents) {
        this.ruleAntecedents = ruleAntecedents;
    }

    public String getConsequent() {
        return consequent;
    }

    public void setConsequent(String consequent) {
        this.consequent = consequent;
    }

    public List<Antecedent> getAntecedents() {
        orderAntecedents();
        return antecedents;
    }

    public void setAntecedents(List<Antecedent> antecedents) {
        this.antecedents = antecedents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long number) {
        this.id = number;
    }

    public Boolean getFlag1() {
        return flag1;
    }

    public void setFlag1(Boolean flag1) {
        this.flag1 = flag1;
    }

    public Boolean getFlag2() {
        return flag2;
    }

    public void setFlag2(Boolean flag2) {
        this.flag2 = flag2;
    }


    public void orderAntecedents(){
        antecedents = new LinkedList<>();
        if (ruleAntecedents != null)
        {
            List<RuleAntecedent> removedList = new ArrayList<>();
            while(true) {
                List<RuleAntecedent> newList = new ArrayList<>(ruleAntecedents);
                newList.removeAll(removedList);
                if (newList.size() > 0) {
                    RuleAntecedent minPos = newList.get(0);
                    for (RuleAntecedent obj : newList) {
                        if (!obj.getServer().equals(server))
                        {
                            removedList.add(obj);
                            continue;
                        }
                        if (obj.getPosition() < minPos.getPosition()) {
                            minPos = obj;
                        }
                    }
                    antecedents.add(minPos.getAssocAntecedent());
                    removedList.add(minPos);
                }
                else{
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String anc;
        sb.append("R").append(Long.toString(id)).append(": ");
        orderAntecedents();
        for(Antecedent antecedent : antecedents) {
            anc = antecedent.getName();
            sb.append(anc).append(", ");
        }
        sb.delete(sb.lastIndexOf(", "), sb.length());
        sb.append(" -> ").append(consequent);
        sb.append(" from ").append(server);
        return sb.toString();
    }

    //Used for listing risk conditions in result string
    public String listAntecedents() {
        StringBuilder result = new StringBuilder();
        orderAntecedents();
        for(int i=0; i<antecedents.size(); i++)
        {
            if (i != antecedents.size() - 1)
            {
                result.append(antecedents.get(i).getName() + ", ");
            }
            else
            {
                result.append(antecedents.get(i).getName());
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule that = (Rule) o;
        return id == that.id &&
                Objects.equals(consequent, that.consequent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consequent);
    }
}
