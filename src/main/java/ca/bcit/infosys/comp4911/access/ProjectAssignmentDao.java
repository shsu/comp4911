package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
import ca.bcit.infosys.comp4911.domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ProjectAssignmentDao {
    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public void create (final ProjectAssignment projectAssignment)
    {
        em.persist(projectAssignment);
    }

    public ProjectAssignment read ( final int projectAssignmentID)
    {
        return em.find(ProjectAssignment.class, projectAssignmentID);
    }

    public void update (final ProjectAssignment projectAssignment)
    {
        em.merge(projectAssignment);
    }

    public void delete (final ProjectAssignment projectAssignment)
    {
        em.remove(read(projectAssignment.getId()));
    }

    public List<ProjectAssignment> getAll() {
        TypedQuery<ProjectAssignment> query = em.createQuery("select p from ProjectAssignment p",
                ProjectAssignment.class);
        return query.getResultList();
    }

    /** Get all Users by Project Id
    /** This doesn't feel right with being in this class, but makes most logical sense I think */
    public List<User> getAllUsers(final int projectNumber) {
        TypedQuery<User> query = em.createQuery("select DISTINCT p.user from ProjectAssignment "
               + "p where p.id = :id",
                User.class);
        query.setParameter("id", projectNumber);
        return query.getResultList();
    }

}
