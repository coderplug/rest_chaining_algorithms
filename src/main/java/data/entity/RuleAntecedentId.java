package data.entity;


import java.io.Serializable;
import java.util.Objects;

public class RuleAntecedentId
        implements Serializable {

    private Long assocRule;

    private Long assocAntecedent;

    //Getters omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RuleAntecedentId that = (RuleAntecedentId) o;
        return Objects.equals(assocRule, that.assocRule) &&
                Objects.equals(assocAntecedent, that.assocAntecedent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assocRule, assocAntecedent);
    }
}
