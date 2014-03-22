package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.ejb.Stateless;

@Stateless
public class ProjectDao {

    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create (final Project project, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(project)) {
            em.persist(project);
        }
    }

    public Project read ( final int projectNumber)
    {
        return em.find(Project.class, projectNumber);
    }

    public void update (final Project project)
    {
        if(ValidationHelper.validateEntity(project)){
            em.merge(project);
        }
    }

    public void delete (final Project project)
    {
        em.remove(read(project.getProjectNumber()));
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

    public List<Project> getAllManagedByUser(int userId)
    {
        TypedQuery<Project> query = em.createQuery("select p from Project p"
            + " where p.projectNumber = (SELECT pa.projectNumber from ProjectAssignment pa"
                                       + " where pa.userId = :userId AND"
                                        + " pa.isProjectManager = TRUE)",
                Project.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
