package data.entity;

//JPA biblioteka
import javax.persistence.*;

//JAXB anotacijų importavimas
import javax.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

//Naudojama surišti objektą su XML elementais
@XmlRootElement
//Nurodoma kaip kuriamas XML failas (pagal laukus), naudojama sąrašų elementams atvaizduoti
@XmlAccessorType(XmlAccessType.FIELD)
//Aprašomos DB užklausos
@NamedQueries({
        @NamedQuery(name = "Rule.findAll", query = "SELECT r FROM Rule r ORDER BY r.server, r.id ASC "),
        @NamedQuery(name = "Rule.findById", query = "SELECT r FROM Rule r WHERE r.id = :id ORDER BY r.server, r.id ASC "),
        @NamedQuery(name = "Rule.findByConsequent", query = "SELECT r FROM Rule r WHERE r.consequent = :consequent ORDER BY r.server, r.id ASC "),
        @NamedQuery(name = "Rule.findByServer", query = "SELECT r FROM Rule r WHERE r.server = :server ORDER BY r.server, r.id ASC")
})
//JPA esybė
@Entity
//Klasė, aprašanti sudėtinį ID
@IdClass(RuleId.class)
//Nurodoma duomenų bazė, lentelė ir schema
@Table(name = "dbs_rule", schema = "public", catalog = "chainingDB")
//Taisyklės klasė
public class Rule implements Serializable {

    //DB stulpelis, atitinkantis lauką
    @Column(name = "consequent")
    //Konsekventas - rezultatas
    private String consequent;

    //Nurodomas sąryšis su RuleAntecedent klase
    @OneToMany(mappedBy = "assocRule")
    //Neatvaizduojamas XML dokumente
    @XmlTransient
    //Sąryšiai su antecedentais
    private List<RuleAntecedent> ruleAntecedents;

    //Sąrašą aprašantis XML elementas
    @XmlElementWrapper(name = "antecedents")
    //Sąrašo dalies XML elememntas
    @XmlElement(name = "antecedent")
    //Neatitinka jokio DB elemento
    @Transient
    //Tiesiogiai pasiekiami antecedentai
    private List<Antecedent> antecedents;

    //ID
    @Id
    //ID generavimo tipas
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Id
    @Column(name = "server")
    //Serveris kuriame egzistuoja taisyklė
    private String server;

    @XmlTransient
    @Transient
    //Flag'as, nurodantis ar naudojama taisyklė
    private Boolean flag1;

    @XmlTransient
    @Transient
    //Flag'as, naudojamas nurodyti ar taisyklės rezultatas egzistuoja tarp faktų
    private Boolean flag2;

    //XML generavimui reikalingas be parametrų konstruktorius
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

    //Užpildomas antecedents laukas
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
        sb.append(server).append(".R").append(Long.toString(id)).append(": ");
        orderAntecedents();
        for(Antecedent antecedent : antecedents) {
            anc = antecedent.getName();
            sb.append(anc).append(", ");
        }
        sb.delete(sb.lastIndexOf(", "), sb.length());
        sb.append(" -> ").append(consequent);
        //sb.append(" from ").append(server);
        return sb.toString();
    }

    //Naudojamas aprašyti taisyklių sąlygas tekstu
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
        Rule rule = (Rule) o;
        return Objects.equals(id, rule.id) &&
                Objects.equals(server, rule.server);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, server);
    }
}
