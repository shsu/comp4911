package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Timesheet;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TimesheetDao {

    @PersistenceContext (unitName = "comp4911")
    EntityManager em;

    public static final String TIMESHEET = Timesheet.class.getSimpleName();

    public void create (final Timesheet ts)
    {
        em.persist(ts);
    }

    public Timesheet read ( final int tsID)
    {
       return em.find(Timesheet.class, tsID);
    }

    public void update (final Timesheet ts)
    {
        em.merge(ts);
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

    public Timesheet getByDate(final int weekNumber, final int year, final int userId) {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t" +
            " where t.weekNumber = :week and t.userID = :id and t.year = :year", Timesheet.class);
        query.setParameter("week", weekNumber);
        query.setParameter("id", userId);
        query.setParameter("year", year);
        return query.getSingleResult();
    }

    public List<Timesheet> getRejected(final Integer userID) {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t where t.isApproved = :approved" +
                " and t.userID = :id",
                Timesheet.class);
        query.setParameter("approved", false);
        query.setParameter("id", userID);
        return query.getResultList();
    }

    public List<Timesheet> getAllTimesheetsToApprove(final int userID) {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t where t.isApproved = :approved" +
                " and t.userID = (select u.id from User u where u.timesheetApproverUserID = :user_id)",
                Timesheet.class);
        query.setParameter("user_id", userID);
        return query.getResultList();
    }
}
