package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.WorkPackageAssignment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Graeme on 2/9/14.
 */
@Stateless
public class WorkPackageAssignmentDao {
    
    @PersistenceContext
        EntityManager em;

    public void create (final WorkPackageAssignment workPackageAssignment)
    {
        em.persist(workPackageAssignment);
    }

    public WorkPackageAssignment read ( final int wpAssignmentID)
    {
        return em.find(WorkPackageAssignment.class, wpAssignmentID);
    }

    public void update (final WorkPackageAssignment workPackageAssignment)
    {
        em.merge(workPackageAssignment);
    }

    public void delete (final WorkPackageAssignment workPackageAssignment)
    {
        em.remove(read(workPackageAssignment.getId()));
    }

    public List<WorkPackageAssignment> getAll() {
        TypedQuery<WorkPackageAssignment> query = em.createQuery("",
                WorkPackageAssignment.class);
        return query.getResultList();
    }
}
