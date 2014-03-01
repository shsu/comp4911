package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
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

    // creating a list even though it is a single result, avoids an exception being thrown on no entity found.
    public List<WorkPackageAssignment> getByUserAndWorkPackage(final WorkPackage workPackage,
                                                         final User user) {
        TypedQuery<WorkPackageAssignment> query = em.createQuery("select w from WorkPackageAssignment w"
                + " where w.workPackage = :workPackage and w.user = :user",
                WorkPackageAssignment.class);
        query.setParameter("workPackage", workPackage);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<WorkPackageAssignment> getAll() {
        TypedQuery<WorkPackageAssignment> query = em.createQuery("select w from WorkPackageAssignment w",
                WorkPackageAssignment.class);
        return query.getResultList();
    }

    public List<WorkPackageAssignment> getAllByUser(final User user) {
        TypedQuery<WorkPackageAssignment> query = em.createQuery("select w from WorkPackageAssignment w where w.user = :user",
                WorkPackageAssignment.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

}
