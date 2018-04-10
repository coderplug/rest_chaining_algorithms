package data.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class RuleAntecedentId
        implements Serializable {

    private Long ruleId;

    private Long antecedentId;

    private String server;
    //Getters omitted for brevity


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleAntecedentId that = (RuleAntecedentId) o;
        return Objects.equals(ruleId, that.ruleId) &&
                Objects.equals(antecedentId, that.antecedentId) &&
                Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ruleId, antecedentId, server);
    }
}
