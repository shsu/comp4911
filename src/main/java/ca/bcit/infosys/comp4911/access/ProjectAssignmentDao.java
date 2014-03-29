package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProjectAssignmentDao {

    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create(final ProjectAssignment projectAssignment,final boolean validate) {
        if (!validate || ValidationHelper.validateEntity(projectAssignment)) {
            em.persist(projectAssignment);
        }
    }

    public ProjectAssignment read(final int projectAssignmentID) {
        return em.find(ProjectAssignment.class, projectAssignmentID);
    }

    public void update(final ProjectAssignment projectAssignment) {
        if(ValidationHelper.validateEntity(projectAssignment)){
            em.merge(projectAssignment);
        }
    }

    public void updateIsProjectManager(final ProjectAssignment projectAssignment, boolean isProjectManager) {
        projectAssignment.setProjectManager(isProjectManager);
        update(projectAssignment);
    }

    public void delete(final ProjectAssignment projectAssignment) {
        em.remove(read(projectAssignment.getId()));
    }

    public ProjectAssignment getByProject(final int projectNumber) {
        TypedQuery<ProjectAssignment> query = em.createQuery("select DISTINCT p from ProjectAssignment "
          + "p where p.projectNumber = :projectNumber",
          ProjectAssignment.class);
        query.setParameter("projectNumber", projectNumber);
        return query.getSingleResult();
    }

    public ProjectAssignment getProjectManager(final int projectNumber) {
        TypedQuery<ProjectAssignment> query = em.createQuery("select DISTINCT p.userId from ProjectAssignment"
            + "p where p.projectNumber = :projectNumber and p.projectManager = true",
                ProjectAssignment.class);
        query.setParameter("projectNumber", projectNumber);
        return query.getSingleResult();
    }

    public List<ProjectAssignment> getAll() {
        TypedQuery<ProjectAssignment> query = em.createQuery("select p from ProjectAssignment p",
          ProjectAssignment.class);
        return query.getResultList();
    }

    public List<ProjectAssignment> getAllByUserId(final int userId){
        TypedQuery<ProjectAssignment> query = em.createQuery("select pa from ProjectAssignment pa" +
                " where pa.userId = :userId and pa.active = true", ProjectAssignment.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * Used to check if a user is a Project Manager for any projects. If they are a manager for a project then the
     * method returns true. This method is used to gather a users permissions.
     * @param userId
     * @return
     */
    public boolean isProjectManager(final int userId) {
        TypedQuery<ProjectAssignment> query = em.createQuery("select pa from ProjectAssignment pa" +
                " where pa.userId = :userId", ProjectAssignment.class);
        query.setParameter("userId", userId);
        ArrayList<ProjectAssignment> projectAssignments = (ArrayList)query.getResultList();
        int length = projectAssignments.size();
        for(int i = 0; i < length; i++) {
            if (projectAssignments.get(i).isProjectManager()) { return true; }
        }

        return false;

    }

}
