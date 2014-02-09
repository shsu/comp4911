package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.PayLevel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Created by Graeme on 2/8/14.
 */
@Stateless
public class PayLevelDao {

    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public void create (final PayLevel payLevel)
    {
        em.persist(payLevel);
    }

    public PayLevel read ( final int payLevelID)
    {
        return em.find(PayLevel.class, payLevelID);
    }

    public void update (final PayLevel payLevel)
    {
        em.merge(payLevel);
    }

    public void delete (final PayLevel payLevel)
    {
        em.remove(read(payLevel.getId()));
    }

    public List<PayLevel> getAll() {
        TypedQuery<PayLevel> query = em.createQuery("",
                PayLevel.class);
        return query.getResultList();
    }
}
