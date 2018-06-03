package data.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

//Naudojama surišti objektą su XML elementais
@XmlRootElement
//Nurodoma kaip kuriamas XML failas (pagal laukus), naudojama sąrašų elementams atvaizduoti
@XmlAccessorType(XmlAccessType.FIELD)
//JPA esybė
@Entity
//Klasė, aprašanti sudėtinį ID
@IdClass(AntecedentId.class)
//Nurodoma duomenų bazė, lentelė ir schema
@Table(name = "dbs_antecedent", schema = "public", catalog = "chainingDB")
//Antecedento klasė
public class Antecedent implements Serializable {

    //ID
    @Id
    //Aprašoma generavimo strategija
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Duomenų bazėje atitinka šį pavadinimą
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    //Antecedento pavadinimas
    private String name;

    @Id
    @Column(name = "server")
    //Serveris, kuriam priklauso antecedentas
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
