package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.domain.WorkPackageAssignment;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class WorkPackageAssignmentDao {
    
    @PersistenceContext
    private EntityManager em;

    public void create (final WorkPackageAssignment workPackageAssignment, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(workPackageAssignment)) {
            em.persist(workPackageAssignment);
        }
    }

    public WorkPackageAssignment read ( final Integer wpAssignmentID)
    {
        return em.find(WorkPackageAssignment.class, wpAssignmentID);
    }

    public void update (final WorkPackageAssignment workPackageAssignment)
    {
        if(ValidationHelper.validateEntity(workPackageAssignment)){
            em.merge(workPackageAssignment);
        }
    }

    public void delete (final WorkPackageAssignment workPackageAssignment)
    {
        em.remove(read(workPackageAssignment.getId()));
    }

    // creating a list even though it is a single result, avoids an exception being thrown on no entity found.
    public List<WorkPackageAssignment> getByUserAndWorkPackage(final String workPackageNumber, final int userId) {
        TypedQuery<WorkPackageAssignment> query = em.createQuery("select w from WorkPackageAssignment w"
                + " where w.workPackageNumber = :workPackageNumber and w.userId = :userId",
                WorkPackageAssignment.class);
        query.setParameter("workPackageNumber", workPackageNumber);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<WorkPackageAssignment> getAll() {
        TypedQuery<WorkPackageAssignment> query = em.createQuery("select w from WorkPackageAssignment w",
                WorkPackageAssignment.class);
        return query.getResultList();
    }

    public List<WorkPackageAssignment> getAllByUser(final int userId) {
        TypedQuery<WorkPackageAssignment> query = em.createQuery("select w from WorkPackageAssignment w " +
                "where w.userId = :userId",
                WorkPackageAssignment.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<User> getAllUsersByWorkPackageNumber(final String workPackageNumber){
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u" +
                " WHERE u.id IN (SELECT wpa.userId FROM WorkPackageAssignment wpa " +
                " WHERE wpa.workPackageNumber = :workPackageNumber)", User.class);
        query.setParameter("workPackageNumber", workPackageNumber);
        return query.getResultList();
    }

    public List<WorkPackageAssignment> getAllByUserId(final int userId){
        TypedQuery<WorkPackageAssignment> query = em.createQuery("select wpa from WorkPackageAssignment wpa" +
                " where wpa.userId = :userId and wpa.active = true", WorkPackageAssignment.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    public boolean isResponsibleEngineer(final int userId){
        TypedQuery<WorkPackageAssignment> query = em.createQuery("Select wpa from WorkPackageAssignment wpa" +
                " where wpa.userId = :userId and wpa.responsibleEngineer = true",
                WorkPackageAssignment.class);
        query.setParameter("userId", userId);
        List<WorkPackageAssignment> responsibleEngineerList = query.getResultList();
        if(responsibleEngineerList.size() > 0) { return true; }
        return false;
    }

}
