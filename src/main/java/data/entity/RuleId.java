package data.entity;

import java.io.Serializable;
import java.util.Objects;

public class RuleId implements Serializable {
    private Long id;
    private String server;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleId ruleId = (RuleId) o;
        return Objects.equals(id, ruleId.id) &&
                Objects.equals(server, ruleId.server);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, server);
    }
}
