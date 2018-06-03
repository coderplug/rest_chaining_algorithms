package data;

//CDI importavimai
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

//JPA importavimai
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.io.Serializable;

//Aplikacijos metu gyvuojantis objektas
@ApplicationScoped
//Skirta JPA objektų kūrimui
public class JPAResources implements Serializable
{
    //Esybių valdymo objektų gamykla
    @PersistenceUnit
    private EntityManagerFactory factory;

    //Gaminamas esybių valdymo objektas
    @Produces
    //Pagrindinis metodas
    @Default
    //Esant poreikiui sukuriamas
    @RequestScoped
    private EntityManager createJTAEntityManager()
    {
        return factory.createEntityManager();
    }

    private void closeUnsynchronizedEntityManager(@Disposes @Default EntityManager em)
    {
        em.close();
    }
}
