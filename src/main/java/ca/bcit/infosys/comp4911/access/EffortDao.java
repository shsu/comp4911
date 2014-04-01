package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Effort;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by craigleclair on 2014-03-29.
 */
@Stateless
public class EffortDao {
    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create (final Effort effort, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(effort)) {
            em.persist(effort);
        }
    }

    public Effort read (final int effortID)
    {
        return em.find(Effort.class, effortID);
    }

    public void update (final Effort effort)
    {
        if(ValidationHelper.validateEntity(effort)){
            em.merge(effort);
        }
    }

    public List<Effort> listOfEffort(final int workDaysMin, final int workDaysMax) {
        List<Effort> listOfEffort = new ArrayList<Effort>();
        listOfEffort.add(getP1(workDaysMin, workDaysMax));
        listOfEffort.add(getP2(workDaysMin, workDaysMax));
        listOfEffort.add(getP3(workDaysMin, workDaysMax));
        listOfEffort.add(getP4(workDaysMin, workDaysMax));
        listOfEffort.add(getP5(workDaysMin, workDaysMax));
        return listOfEffort;

    }

    private Effort getP1 (final int workDaysMin, final int workDaysMax) {
        TypedQuery<Effort> query = em.createQuery("Select e From Effort e where e.pLevel = ca.bcit.infosys.comp4911.domain.PLevel.P1" +
                " and e.personDays >= :workDaysMin and" +
                " e.personDays <= :workDaysMax"  , Effort.class);
        query.setParameter("workDaysMin", workDaysMin);
        query.setParameter("workDaysMax", workDaysMax);
        return query.getSingleResult();
    }

    private Effort getP2 (final int workDaysMin, final int workDaysMax) {
        TypedQuery<Effort> query = em.createQuery("Select e From Effort e where e.pLevel = ca.bcit.infosys.comp4911.domain.PLevel.P2" +
                " and e.personDays >= :workDaysMin and" +
                " e.personDays <= :workDaysMax"  , Effort.class);
        query.setParameter("workDaysMin", workDaysMin);
        query.setParameter("workDaysMax", workDaysMax);
        return query.getSingleResult();
    }

    private Effort getP3 (final int workDaysMin, final int workDaysMax) {
        TypedQuery<Effort> query = em.createQuery("Select e From Effort e where e.pLevel = ca.bcit.infosys.comp4911.domain.PLevel.P3" +
                " and e.personDays >= :workDaysMin and" +
                " e.personDays <= :workDaysMax"  , Effort.class);
        query.setParameter("workDaysMin", workDaysMin);
        query.setParameter("workDaysMax", workDaysMax);
        return query.getSingleResult();
    }

    private Effort getP4 (final int workDaysMin, final int workDaysMax) {
        TypedQuery<Effort> query = em.createQuery("Select e From Effort e where e.pLevel = ca.bcit.infosys.comp4911.domain.PLevel.P4" +
                " and e.personDays >= :workDaysMin and" +
                " e.personDays <= :workDaysMax"  , Effort.class);
        query.setParameter("workDaysMin", workDaysMin);
        query.setParameter("workDaysMax", workDaysMax);
        return query.getSingleResult();
    }

    private Effort getP5 (final int workDaysMin, final int workDaysMax) {
        TypedQuery<Effort> query = em.createQuery("Select e From Effort e where e.pLevel = ca.bcit.infosys.comp4911.domain.PLevel.P5" +
                " and e.personDays >= :workDaysMin and" +
                " e.personDays <= :workDaysMax"  , Effort.class);
        query.setParameter("workDaysMin", workDaysMin);
        query.setParameter("workDaysMax", workDaysMax);
        return query.getSingleResult();
    }
}
