package data.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) //Needed to show List values correctly
@Entity
@IdClass(AntecedentId.class)
@Table(name = "dbs_antecedent", schema = "public", catalog = "chainingDB")
public class Antecedent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "server")
    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antecedent that = (Antecedent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(server, that.server);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, server);
    }
}
