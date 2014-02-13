package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.WorkPackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Graeme on 2/8/14.
 */
@Stateless
public class WorkPackageDao {

    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public static final String WORKPACKAGE = WorkPackage.class.getSimpleName();

    public void create (final WorkPackage workPackage)
    {
        em.persist(workPackage);
    }

    public WorkPackage read ( final int workPackageID)
    {
        return em.find(WorkPackage.class, workPackageID);
    }

    public void update (final WorkPackage workPackage)
    {
        em.merge(workPackage);
    }

    public void delete (final WorkPackage workPackage)
    {
        em.remove(read(workPackage.getId()));
    }

    public List<WorkPackage> getAll() {
        TypedQuery<WorkPackage> query = em.createQuery("select wp from " + WORKPACKAGE + " wp",
                WorkPackage.class);
        return query.getResultList();
    }
}
