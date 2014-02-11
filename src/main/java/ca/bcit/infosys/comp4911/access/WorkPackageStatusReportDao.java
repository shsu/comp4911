package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.WorkPackageStatusReport;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Graeme on 2/9/14.
 */
@Stateless
public class WorkPackageStatusReportDao {

    @PersistenceContext
    EntityManager em;

    public void create (final WorkPackageStatusReport workPackageStatusReport)
    {
        em.persist(workPackageStatusReport);
    }

    public WorkPackageStatusReport read ( final int workPackageStatusReportID)
    {
        return em.find(WorkPackageStatusReport.class, workPackageStatusReportID);
    }

    public void update (final WorkPackageStatusReport workPackageStatusReport)
    {
        em.merge(workPackageStatusReport);
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
}
