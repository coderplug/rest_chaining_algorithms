package main.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rule_antecedent")
@IdClass(RuleAntecedentId.class)
public class RuleAntecedent {

    @Id
    @ManyToOne
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    private Rule assocRule;

    @Id
    @ManyToOne
    @JoinColumn(name = "antecedent_id", referencedColumnName = "id")
    private Antecedent assocAntecedent;

    @Column(name = "antecedent_position")
    private Long position;

    public RuleAntecedent() {}

    public RuleAntecedent(Rule rule, Antecedent antecedent) {
        this.assocRule = rule;
        this.assocAntecedent = antecedent;
    }

    //Getters and setters omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RuleAntecedent that = (RuleAntecedent) o;
        return Objects.equals(assocRule, that.assocRule) &&
                Objects.equals(assocAntecedent, that.assocAntecedent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assocRule, assocAntecedent);
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
}
