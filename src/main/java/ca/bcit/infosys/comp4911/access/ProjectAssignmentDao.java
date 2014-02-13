package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
import ca.bcit.infosys.comp4911.domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Graeme on 2/12/14.
 */
@Stateless
public class ProjectAssignmentDao {
    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public static final String PROJECT_ASSIGNMENT = ProjectAssignment.class.getSimpleName();

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
        TypedQuery<ProjectAssignment> query = em.createQuery("select p from " + PROJECT_ASSIGNMENT + " p",
                ProjectAssignment.class);
        return query.getResultList();
    }

    public List<ProjectAssignment> getAllByUser(final User user) {
        TypedQuery<ProjectAssignment> query = em.createQuery("select p from " + PROJECT_ASSIGNMENT + " p where p.user = :user",
                ProjectAssignment.class);
        query.setParameter("user", user);
        return query.getResultList();
    }
}
