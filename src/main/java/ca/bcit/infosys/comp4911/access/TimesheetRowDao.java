package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.domain.TimesheetRow;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TimesheetRowDao {

    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public void create (final TimesheetRow row)
    {
        em.persist(row);
    }

    public TimesheetRow read ( final int rowID)
    {
        return em.find(TimesheetRow.class, rowID);
    }

    public void update (final TimesheetRow row)
    {
        em.merge(row);
    }

    public void delete (final TimesheetRow row)
    {
        em.remove(read(row.getId()));
    }


    public List<TimesheetRow> getAll() {
        TypedQuery<TimesheetRow> query = em.createQuery("select t from TimesheetRow t",
                TimesheetRow.class);
        return query.getResultList();
    }

    public List<TimesheetRow> getAllById(final Timesheet timesheet) {
        TypedQuery<TimesheetRow> query = em.createQuery("select t from TimesheetRow t where t.timesheet = :timesheet ",
                TimesheetRow.class);
        query.setParameter("timesheet", timesheet);
        return query.getResultList();
    }

    public List<TimesheetRow> getAllByWP(final String wpNumber){
        TypedQuery<TimesheetRow> query = em.createQuery("select t from TimesheetRow" +
                " t where t.workPackageNumber = :wpNumber", TimesheetRow.class);
        query.setParameter("wpNumber", wpNumber);
        return query.getResultList();
    }

    public List<TimesheetRow> getAllByProject(final String projectNumber){
    TypedQuery<TimesheetRow> query = em.createQuery("select t from TimesheetRow" +
            " t where t.workPackageNumber = :projectNumber", TimesheetRow.class);
    query.setParameter("projectNumber", projectNumber);
    return query.getResultList();
    }
}
