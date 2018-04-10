package data.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "dbs_rule_antecedent")
@IdClass(RuleAntecedentId.class)
public class RuleAntecedent implements Serializable {

    @Id
    @Column(name = "rule_id")
    private Long ruleId;

    @Id
    @Column(name = "antecedent_id")
    private Long antecedentId;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "rule_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "server", referencedColumnName = "server", insertable = false, updatable = false)
    })
    private Rule assocRule;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "antecedent_id", referencedColumnName = "id", insertable = false, updatable = false),
            @JoinColumn(name = "server", referencedColumnName = "server", insertable = false, updatable = false)
    })
    private Antecedent assocAntecedent;

    @Column(name = "antecedent_position")
    private Long position;

    @Id
    @Column(name = "server")
    private String server;

    //Getters and setters omitted for brevity

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
