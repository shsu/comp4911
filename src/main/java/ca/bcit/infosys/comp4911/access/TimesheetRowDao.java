package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.domain.TimesheetRow;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class TimesheetRowDao {

    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public void create (final TimesheetRow row, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(row)) {
            em.persist(row);
        }
    }

    public TimesheetRow read ( final int rowID)
    {
        return em.find(TimesheetRow.class, rowID);
    }

    public void update (final TimesheetRow row)
    {
        if(ValidationHelper.validateEntity(row)){
            em.merge(row);
        }
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

    public List<TimesheetRow> getAllByProject(final int projectNumber){
    TypedQuery<TimesheetRow> query = em.createQuery("select t from TimesheetRow" +
            " t where t.projectNumber = :projectNumber", TimesheetRow.class);
    query.setParameter("projectNumber", projectNumber);
    return query.getResultList();
    }

    public List<TimesheetRow> getTimesheetRowsByWP(final String workpackageId){
        TypedQuery<TimesheetRow> query = em.createQuery("SELECT tsr from TimesheetRow tsr" +
                " WHERE tsr.workPackageNumber LIKE :workpackageId", TimesheetRow.class);
        query.setParameter("workpackageId", workpackageId);
        return query.getResultList();
    }

    public boolean isParentLeaf(String wpNumber) {
        String parentNumber = wpNumber;
        for(int i = 0; i < 7; i++){
            //find first occurrence of 0
            if(wpNumber.charAt(i) == '0') {
                parentNumber = wpNumber.substring(0, i-1);
                for(int j = 0; j < 7 - parentNumber.length(); j++){
                    parentNumber += '0';
                }
                break;
            }

        }
        return getTimesheetRowsByWP(parentNumber).size() > 0;
    }


}
