package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Created by Graeme on 2/8/14.
 */
@Stateless
public class ProjectDao {

    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public static final String PROJECT = Project.class.getSimpleName();

    public void create (final Project project)
    {
        em.persist(project);
    }

    public Project read ( final String projectID)
    {
        return em.find(Project.class, projectID);
    }

    public void update (final Project project)
    {
        em.merge(project);
    }

    public void delete (final Project project)
    {
        em.remove(read(project.getProjectName()));
    }

    public List<Project> getAll() {
        TypedQuery<Project> query = em.createQuery("select p from Project p",
                Project.class);
        return query.getResultList();
    }

    public List<Project> getAllByUser(int userId)
    {
        TypedQuery<Project> query = em.createQuery("select p from Project p "
                + "where p.projectNumber = (SELECT i.projectNumber from ProjectAssignment i"
                                             + " where i.userId = :userId)",
                Project.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
