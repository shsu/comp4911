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
    private EntityManager em;

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
            " where t.weekNumber = :week and t.userId = :userId and t.year = :year", Timesheet.class);
        query.setParameter("week", weekNumber);
        query.setParameter("userId", userId);
        query.setParameter("year", year);
        return query.getSingleResult();
    }

    public List<Timesheet> getRejected(final Integer userId) {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t where t.isApproved = :approved" +
                " and t.userId = :userId",
                Timesheet.class);
        query.setParameter("approved", false);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<Timesheet> getAllTimesheetsToApprove(final int userId) {
        TypedQuery<Timesheet> query = em.createQuery("select t from Timesheet t where t.isApproved = :approved" +
                " and t.userId = (select u.id from User u where u.timesheetApproverUserID = :userId)",
                Timesheet.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<Timesheet> getTimesheetsByWP(final String workpackageId){
        TypedQuery<Timesheet> query = em.createQuery("SELECT DISTINCT t from Timesheet t" +
                " JOIN TimesheetRow tr ON t.id = tr.TimesheetId" +
                " WHERE tr.workPackageNumber = :wpId", Timesheet.class);
        query.setParameter("wpId", workpackageId);
        return query.getResultList();
    }


}
