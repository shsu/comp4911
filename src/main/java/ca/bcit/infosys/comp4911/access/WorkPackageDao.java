package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class WorkPackageDao {

    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create(final WorkPackage workPackage, final boolean validate) {
        if (!validate || ValidationHelper.validateEntity(workPackage)) {
            em.persist(workPackage);
        }
    }

    public WorkPackage read(final String workPackageNumber) {
        return em.find(WorkPackage.class, workPackageNumber);
    }

    public void update(final WorkPackage workPackage) {
        if(ValidationHelper.validateEntity(workPackage)){
            em.merge(workPackage);
        }
    }

    public void delete(final WorkPackage workPackage) {
        em.remove(read(workPackage.getWorkPackageName()));
    }

    public List<WorkPackage> getAll() {
        TypedQuery<WorkPackage> query = em.createQuery("select wp from WorkPackage wp",
          WorkPackage.class);
        return query.getResultList();
    }

    public List<WorkPackage> getAllByUser(int userId) {
        TypedQuery<WorkPackage> query = em.createQuery("select w from WorkPackage w "
          + "where w.workPackageNumber = (SELECT i.workPackageNumber from WorkPackageAssignment i"
          + " where i.userId = :userId)",
          WorkPackage.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<WorkPackage> getAllByEngineer(int userId) {
        TypedQuery<WorkPackage> query = em.createQuery("select w from WorkPackage w "
                + "where w.workPackageNumber = (SELECT i.workPackageNumber from WorkPackageAssignment i"
                + " where i.userId = :userId and i.isResponsibleEngineer = :isEngineer)",
                WorkPackage.class);
        query.setParameter("userId", userId);
        query.setParameter("isEngineer", true);
        return query.getResultList();
    }

    public List<WorkPackage> getWPsByWorkPackageNumbers(List<String> wpNumbers){
        TypedQuery<WorkPackage> query = em.createQuery("select w from WorkPackage w" +
                " where w.workPackageNumber IN (:wpNumbers)", WorkPackage.class);
        query.setParameter("wpNumbers", wpNumbers);

        return query.getResultList();
    }

    public List<WorkPackage> getAllByProject(int projectNumber) {
        TypedQuery<WorkPackage> query = em.createQuery("select wp from WorkPackage wp"
                + " where wp.projectNumber = :projectNumber",
                WorkPackage.class);
        query.setParameter("projectNumber", projectNumber);
        return query.getResultList();
    }

    public List<String> getWPChildren(String wPNumber) {
        String findChildren = wPNumber;
        for(int i = 0; i < wPNumber.length(); i++){
            if(wPNumber.charAt(i) == '0'){
                findChildren = wPNumber.substring(0, i-1) + "%";
                break;
            }
        }
        TypedQuery<String> query = em.createQuery("select wp.workPackageNumber from WorkPackage wp" +
                " where wp.workPackageNumber like :findChildren", String.class);
        query.setParameter("findChildren", findChildren);
        return query.getResultList();
    }
}
