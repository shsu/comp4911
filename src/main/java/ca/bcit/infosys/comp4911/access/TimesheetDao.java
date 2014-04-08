package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TimesheetDao {

    @PersistenceContext (unitName = "comp4911")
    private EntityManager em;

    public void create (final Timesheet ts, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(ts)) {
            em.persist(ts);
        }
    }

    public Timesheet read ( final int tsID)
    {
       return em.find(Timesheet.class, tsID);
    }

    public void update (final Timesheet ts)
    {
        if(ValidationHelper.validateEntity(ts)){
            em.merge(ts);
        }
    }

    public void delete (final Timesheet ts)
    {
        em.remove(read(ts.getId()));
    }

    public List<Timesheet> getAll() {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t",
                Timesheet.class);
        return query.getResultList();
    }

    public List<Timesheet> getAllByUser(final int userId)
    {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t"
                + " where t.userId = :id", Timesheet.class);
        query.setParameter("id", userId);
        return query.getResultList();
    }

    public List<Timesheet> getByDate(final int weekNumber, final int year, final int userId) {
        System.out.println("" + weekNumber + " " + year + " " + userId);
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t" +
            " where t.weekNumber = :week and t.userId = :userId and t.year = :year",
                Timesheet.class);
        query.setParameter("week", weekNumber);
        query.setParameter("userId", userId);
        query.setParameter("year", year);
        return query.getResultList();
    }

    public List<Timesheet> getRejected(final Integer userId) {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t where t.approved = :approved" +
                " and t.userId = :userId and t.pending = false",
                Timesheet.class);
        query.setParameter("approved", false);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<Timesheet> getAllTimesheetsToApprove(final int userId) {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t where t.userId" +
                " IN (select u.id from User u where u.timesheetApproverUserID = :userId) AND t.approved = :approved" +
                " AND t.signed = :signed AND t.pending = :pending", Timesheet.class);
        query.setParameter("signed", true);
        query.setParameter("approved", false);
        query.setParameter("userId", userId);
        query.setParameter("pending", true);
        return query.getResultList();
    }

}
