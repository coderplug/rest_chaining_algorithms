package data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

//JPA esybė
@Entity
//DB lentelė
@Table(name = "dbs_rule_antecedent")
//Klasė, aprašanti sudėtinį ID
@IdClass(RuleAntecedentId.class)
//Klasė, aprašanti sąryšį tarp taisyklių ir antecedentų
public class RuleAntecedent implements Serializable {

    //ID
    @Id
    //Lentelės stulpelio pavadinimas
    @Column(name = "rule_id")
    //Taisyklės ID
    private Long ruleId;

    @Id
    @Column(name = "antecedent_id")
    //Antecedento ID
    private Long antecedentId;

    //n-1 sąryšis
    @ManyToOne
    //Aprašoma kaip jungti lenteles (pagrindinių raktų sąryšiai)
    @JoinColumns({
            @JoinColumn(name = "rule_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "server", referencedColumnName = "server", insertable = false, updatable = false)
    })
    //Sąryšio taisyklė
    private Rule assocRule;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "antecedent_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "server", referencedColumnName = "server", insertable = false, updatable = false)
    })
    //Sąšysio antecedentas
    private Antecedent assocAntecedent;

    @Column(name = "antecedent_position")
    //Antecedento pozicija
    private Long position;

    @Id
    @Column(name = "server")
    //Sąryšio serveris
    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Rule getAssocRule() {
        return assocRule;
    }

    public void setAssocRule(Rule assocRule) {
        this.assocRule = assocRule;
    }

    public Antecedent getAssocAntecedent() {
        return assocAntecedent;
    }

    public void setAssocAntecedent(Antecedent assocAntecedent) {
        this.assocAntecedent = assocAntecedent;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleAntecedent that = (RuleAntecedent) o;
        return ruleId == that.ruleId &&
                antecedentId == that.antecedentId &&
                Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ruleId, antecedentId, server);
    }
}
