package data;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.SynchronizationType;
import java.io.Serializable;

@ApplicationScoped
public class JPAResources implements Serializable
{
    @PersistenceUnit
    private EntityManagerFactory factory;

    @Produces
    @Default
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
