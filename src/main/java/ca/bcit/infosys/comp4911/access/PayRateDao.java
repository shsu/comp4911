package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.PayRate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Created by Graeme on 2/8/14.
 */
@Stateless
public class PayRateDao {

    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public static final String PAYRATE = PayRate.class.getSimpleName();

    public void create (final PayRate payRate)
    {
        em.persist(payRate);
    }

    public PayRate read ( final PayRate payRateID)
    {
        return em.find(PayRate.class, payRateID);
    }

    public void update (final PayRate payRate)
    {
        em.merge(payRate);
    }

    public List<PayRate> getAllPayRates() {
        TypedQuery<PayRate> query = em.createQuery("select p from " + PAYRATE + " p",
                PayRate.class);
        return query.getResultList();
    }

    public List<PayRate> getAllPayRatesByLevel(final String payLevel) {
        TypedQuery<PayRate> query = em.createQuery("select p from " + PAYRATE + " p where p.payLevelName = :payLevel ",
                PayRate.class);
        query.setParameter("payLevel", payLevel);
        return query.getResultList();
    }

    public PayRate getPayRateByLevelAndYear(final String payLevel,
                                               final String year) {
        TypedQuery<PayRate> query = em.createQuery("select p from " + PAYRATE + " p where p.payLevelName = :payLevel" +
            " and p.year = :year", PayRate.class);
        query.setParameter("payLevel", payLevel);
        query.setParameter("year", year);
        return query.getSingleResult();
    }
}