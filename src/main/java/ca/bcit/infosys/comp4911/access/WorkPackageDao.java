package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

    public WorkPackage update(final WorkPackage workPackage) {
        if(ValidationHelper.validateEntity(workPackage)){
            return em.merge(workPackage);
        }
        return null;
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
          + "where w.workPackageNumber IN (SELECT i.workPackageNumber from WorkPackageAssignment i"
          + " where i.userId = :userId)",
          WorkPackage.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<WorkPackage> getAllByEngineer(int userId) {
        TypedQuery<WorkPackage> query = em.createQuery("select w from WorkPackage w "
                + "where w.workPackageNumber IN (SELECT i.workPackageNumber from WorkPackageAssignment i"
                + " where i.userId = :userId and i.responsibleEngineer = :isEngineer)",
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

    public List<WorkPackage> getAllProjectWPLeafs(final int projectNumber) {
        TypedQuery<WorkPackage> query = em.createQuery("select w from WorkPackage w where w.workPackageNumber IN" +
                " (select Distinct tsr.workPackageNumber from TimesheetRow tsr" +
                " where tsr.projectNumber = :projectNumber)", WorkPackage.class);
        query.setParameter("projectNumber", projectNumber);
        return query.getResultList();
    }

    public List<String> getWPChildren(String wPNumber) {
        String findChildren = wPNumber;

        if(wPNumber.charAt(6) == '0'){
            for(int i = 0; i < wPNumber.length(); i++){
                if(wPNumber.charAt(i) == '0'){
                    findChildren = wPNumber.substring(0, i) + "%";
                    break;
                }
            }
        }

        TypedQuery<String> query = em.createQuery("select wp.workPackageNumber from WorkPackage wp" +
                " where wp.workPackageNumber like :findChildren", String.class);
        query.setParameter("findChildren", findChildren);
        return query.getResultList();
    }



    public HashMap<String, Date> getWPNumberDateHash(int projectNumber) {
        TypedQuery<WorkPackage> query = em.createQuery("select wp from WorkPackage wp"
                + " where wp.projectNumber = :projectNumber",
                WorkPackage.class);
        query.setParameter("projectNumber", projectNumber);

        List<WorkPackage> listOfWorkPackages = query.getResultList();
        Iterator<WorkPackage> workPackageIterator = listOfWorkPackages.listIterator();
        HashMap<String, Date> wPNumbersAndDates = new HashMap<String, Date>();
        WorkPackage workPackage;
        while(workPackageIterator.hasNext()) {
            workPackage = workPackageIterator.next();
            wPNumbersAndDates.put(workPackage.getWorkPackageNumber(), workPackage.getIssueDate());
        }

        return wPNumbersAndDates;
    }

    public HashMap<String, String> getWPNumberNameHash(List<String> wPNumbers) {
        HashMap<String, String> wPNumberDescriptionHash = new HashMap<String, String>();
        List<WorkPackage> workPackages = getWPsByWorkPackageNumbers(wPNumbers);
        Iterator<WorkPackage> workPackageIterator = workPackages.listIterator();
        WorkPackage workPackage;
        while(workPackageIterator.hasNext()){
            workPackage = workPackageIterator.next();
            wPNumberDescriptionHash.put(workPackage.getWorkPackageNumber(), workPackage.getWorkPackageName());
        }

        return wPNumberDescriptionHash;
    }

    public boolean isParentLeaf(String wpNumber) {
        String parentNumber = wpNumber;
        for(int i = 0; i < 7; i++){
            //find first occurrence of 0
            if(wpNumber.charAt(i) == '0') {
                parentNumber = wpNumber.substring(0, i-1);
                for(int j = 0; j <= 7 - parentNumber.length(); j++){
                    parentNumber += '0';
                }
                break;
            }

        }

        return read(parentNumber) != null && read(parentNumber).getEstimateAtStart().size() != 0;
    }

    public boolean doesParentExist(String wpNumber) {
        String parentNumber = wpNumber;

        if(wpNumber.charAt(1) != '0' && wpNumber.charAt(2) == '0'){
            return true;
        }
        else if(wpNumber.charAt(1) != '0') {
            for(int i = 0; i < 7; i++){
                //find first occurrence of 0
                if(wpNumber.charAt(i) == '0') {
                    parentNumber = wpNumber.substring(0, i-1);
                    int length = parentNumber.length();
                    for(int j = 0; j < 7 - length; j++){
                        parentNumber += '0';
                    }
                    break;
                }

            }
        }

        return read(parentNumber) != null;
    }
}
