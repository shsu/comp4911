package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.WorkPackageStatusReport;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class WorkPackageStatusReportDao {

    final public static int MAX_SIZE = 20;

    @PersistenceContext
    private EntityManager em;

    public void create (final WorkPackageStatusReport workPackageStatusReport, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(workPackageStatusReport)) {
            em.persist(workPackageStatusReport);
        }
    }

    public WorkPackageStatusReport read ( final int workPackageStatusReportID)
    {
        return em.find(WorkPackageStatusReport.class, workPackageStatusReportID);
    }

    public void update (final WorkPackageStatusReport workPackageStatusReport)
    {
        if(ValidationHelper.validateEntity(workPackageStatusReport)){
            em.merge(workPackageStatusReport);
        }
    }

    public void delete (final WorkPackageStatusReport workPackageStatusReport)
    {
        em.remove(read(workPackageStatusReport.getId()));
    }

    public List<WorkPackageStatusReport> getAll() {
        TypedQuery<WorkPackageStatusReport> query = em.createQuery("select r from WorkPackageStatusReport r",
                WorkPackageStatusReport.class);
        return query.getResultList();
    }

    public List<WorkPackageStatusReport> getAllByWorkPackage(final String workPackageNumber) {
        TypedQuery<WorkPackageStatusReport> query = em.createQuery("select r from WorkPackageStatusReport r"
                + " where r.workPackageNumber = :workPackageNumber",
                WorkPackageStatusReport.class);
        query.setParameter("workPackageNumber", workPackageNumber);
        return query.getResultList();
    }

    public List<String> getLatestTwentyByProject(final int projectNumber) {
        TypedQuery<String> query = em.createQuery("select r.workPackageNumber from WorkPackageStatusReport r" +
            " where r.workPackageNumber IN" +
            " (select w.workPackageNumber from WorkPackage w where w.projectNumber = :projectNumber)"  +
            " ORDER BY r.reportDate", String.class);
        query.setParameter("projectNumber", projectNumber);
        query.setMaxResults(MAX_SIZE);
        return query.getResultList();
    }

    public List<WorkPackageStatusReport> getAllByProject(final int projectNumber) {
        TypedQuery<WorkPackageStatusReport> query = em.createQuery("select r from WorkPackageStatusReport r" +
                " where r.workPackageNumber IN" +
                " (select w.workPackageNumber from WorkPackage w where w.projectNumber = :projectNumber)"  +
                " ORDER BY r.reportDate", WorkPackageStatusReport.class);
        query.setParameter("projectNumber", projectNumber);
        return query.getResultList();
    }

    public List<WorkPackageStatusReport> getAllByMultipleWPNumber(List<String> wPNumbers){
        TypedQuery<WorkPackageStatusReport> query = em.createQuery("select wpsr from WorkPackageStatusReport wpsr" +
                " where wpsr.workPackageNumber IN (:wPNumbers)", WorkPackageStatusReport.class);
        query.setParameter("wPNumbers", wPNumbers);
        return query.getResultList();

    }

}
