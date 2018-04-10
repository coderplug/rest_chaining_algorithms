package data.entity;

import java.io.Serializable;
import java.util.Objects;

public class AntecedentId implements Serializable {
    private Long id;
    private String server;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntecedentId that = (AntecedentId) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, server);
    }
}
