package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.PLevel;
import ca.bcit.infosys.comp4911.domain.PayRate;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class PayRateDao {

    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create (final PayRate payRate, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(payRate)) {
            em.persist(payRate);
        }
    }

    public PayRate read ( final int payRateID)
    {
        return em.find(PayRate.class, payRateID);
    }

    public void update (final PayRate payRate)
    {
        if(ValidationHelper.validateEntity(payRate)){
            em.merge(payRate);
        }
    }

    public List<PayRate> getAllPayRates() {
        TypedQuery<PayRate> query = em.createQuery("select p from PayRate p",
                PayRate.class);
        return query.getResultList();
    }

    public List<PayRate> getAllPayRatesByLevel(final String pLevel) {
        TypedQuery<PayRate> query = em.createQuery("select p from PayRate p where p.pLevel = :pLevel ",
                PayRate.class);
        query.setParameter("pLevel", PLevel.valueOf(pLevel));
        return query.getResultList();
    }


    /**
     * Does it matter for this method that year is passed in as a string an not an integer? It's stored in the
     * DB as an integer. Wanted to make a note because I know I'll forget.
     * @param pLevel
     * @param year
     * @return
     */
    public PayRate getPayRateByLevelAndYear(final String pLevel,
                                               final String year) {
        TypedQuery<PayRate> query = em.createQuery("select p from PayRate p where p.pLevel = :pLevel" +
            " and p.year = :year", PayRate.class);
        query.setParameter("pLevel", PLevel.valueOf(pLevel));
        query.setParameter("year", year);
        return query.getSingleResult();
    }

    public List<PayRate> getPayRateByYear(final int year) {
        TypedQuery<PayRate> query = em.createQuery("SELECT p FROM PayRate p WHERE p.year = :year" +
                " ORDER BY p.pLevel", PayRate.class);
        query.setParameter("year", year);
        return query.getResultList();
    }

    public HashMap<PLevel, BigDecimal> getPayRateHashByYear(final int year) {
        List<PayRate> payRates = getPayRateByYear(year);
        Iterator<PayRate> payRateIterator = payRates.listIterator();
        PayRate payRate;
        HashMap<PLevel, BigDecimal> pLevelIntegerHashMap = new HashMap<PLevel, BigDecimal>();
        while(payRateIterator.hasNext()){
            payRate = payRateIterator.next();
            pLevelIntegerHashMap.put(payRate.getpLevel(), payRate.getRate());
        }
        return pLevelIntegerHashMap;
    }
}
