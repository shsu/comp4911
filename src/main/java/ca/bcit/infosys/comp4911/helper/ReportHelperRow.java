package ca.bcit.infosys.comp4911.helper;

import ca.bcit.infosys.comp4911.access.PayRateDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.domain.*;
import org.joda.time.DateTime;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A project manager needs to see the most recent WPs associated with a project, and how much work has been
 * done for each work package within the project.
 * Created by craigleclair on 3/14/2014.
 */
public class ReportHelperRow {

    private WorkPackageDao workPackageDao;

    private PayRateDao payRateDao;

    private HashMap<PLevel, Integer> pLevels;
    private double labourDollars;
    private String wpNumber;
    private String wpDescription;

    public ReportHelperRow(WorkPackageDao workPackageDao, PayRateDao payRateDao) {
        this.workPackageDao = workPackageDao;
        this.payRateDao = payRateDao;
        pLevels = new HashMap<PLevel, Integer>();
        pLevels.put(PLevel.P1, 0);
        pLevels.put(PLevel.P2, 0);
        pLevels.put(PLevel.P3, 0);
        pLevels.put(PLevel.P4, 0);
        pLevels.put(PLevel.P5, 0);
        pLevels.put(PLevel.SS, 0);
        pLevels.put(PLevel.DS, 0);
        labourDollars = 0;
    }

    public void incrementP1(int hours){
        pLevels.put(PLevel.P1, pLevels.get(PLevel.P1) + hours);
    }

    public void incrementP2(int hours){
        pLevels.put(PLevel.P2, pLevels.get(PLevel.P2) + hours);
    }

    public void incrementP3(int hours){
        pLevels.put(PLevel.P3, pLevels.get(PLevel.P3) + hours);
    }

    public void incrementP4(int hours){
        pLevels.put(PLevel.P4, pLevels.get(PLevel.P4) + hours);
    }

    public void incrementP5(int hours){
        pLevels.put(PLevel.P5, pLevels.get(PLevel.P5) + hours);
    }

    public void incrementSS(int hours){
        pLevels.put(PLevel.SS, pLevels.get(PLevel.SS) + hours);
    }

    public void incrementDS(int hours){
        pLevels.put(PLevel.DS, pLevels.get(PLevel.DS) + hours);
    }

    public double getLabourDollars() {
        return labourDollars;
    }

    public void setLabourDollars(double labourDollars) {
        this.labourDollars = labourDollars;
    }

    public String getWpNumber() {
        return wpNumber;
    }

    public void setWpNumber(String wpNumber) {
        this.wpNumber = wpNumber;
    }

    public String getWpDescription() {
        return wpDescription;
    }

    public void setWpDescription(String wpDescription) {
        this.wpDescription = wpDescription;
    }

    public HashMap<PLevel, Integer> getpLevels() { return pLevels; }

    public void setpLevels(HashMap<PLevel, Integer> pLevels) { this.pLevels = pLevels; }

    public void calculateExpectedPLevelTotalsFromWPSRs(List<WorkPackageStatusReport> oneStatusReportPerWP){
        Iterator<WorkPackageStatusReport> wpsrIterator = oneStatusReportPerWP.listIterator();
        WorkPackageStatusReport wpsr;
        while(wpsrIterator.hasNext()){
            wpsr = wpsrIterator.next();
            increasePLevelsFromEffortList(wpsr.getEstimatedWorkRemainingInPD(), wpsr.getReportDate());
        }
    }

    /**
     * Gets the amount of PLevel days per a list of Timesheets. This iterates through all of the Timesheets associated
     * with a specific Work Package. It then updates the amount of PLevels used per work package.
     * @param timesheetRowList, wpNumber
     * @return - the finished reportHelperRow
     */
    public void calculatePersonHours(List<TimesheetRow> timesheetRowList){
        Iterator<TimesheetRow> timesheetRowIterator = timesheetRowList.iterator();
        TimesheetRow tsr;
        WorkPackage workPackage;
        while(timesheetRowIterator.hasNext())
        {
            tsr = timesheetRowIterator.next();
            workPackage = workPackageDao.read(tsr.getWorkPackageNumber());
            increasePLevel(getPLevelBigDecimalHashForYear(workPackage.getIssueDate()),
                    tsr.getpLevel(), tsr.calculateTotal());
        }
    }

    public void calculateInitialBudget(List<WorkPackage> projectWorkPackages){
        Iterator<WorkPackage> wpIterator = projectWorkPackages.listIterator();
        WorkPackage projectWorkPackage;
        while(wpIterator.hasNext()){
            projectWorkPackage = wpIterator.next();
            increasePLevelsFromEffortList(projectWorkPackage.getEstimateAtStart(),
                    projectWorkPackage.getIssueDate());
        }
    }

    private void increasePLevelsFromEffortList(List<Effort> effortList, Date date){
        Iterator<Effort> effortIterator;
        effortIterator = effortList.listIterator();
        Effort effort;
        while(effortIterator.hasNext()){
            effort = effortIterator.next();
            increasePLevel(getPLevelBigDecimalHashForYear(date),
                    effort.getpLevel(), effort.getPersonDays());

        }
    }

    public HashMap<PLevel, BigDecimal> getPLevelBigDecimalHashForYear(Date date){
        DateTime dateTime = new DateTime(date.toString());
        return payRateDao.getPayRateHashByYear(dateTime.getYear());
    }

    public void increasePLevel(HashMap<PLevel, BigDecimal> yearPayRate, PLevel pLevel, int hours){

        switch(pLevel){
            case P1:
                incrementP1(hours);
                labourDollars += yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P2:
                incrementP2(hours);
                labourDollars += yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P3:
                incrementP3(hours);
                labourDollars += yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P4:
                incrementP4(hours);
                labourDollars += yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P5:
                incrementP5(hours);
                labourDollars += yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case SS:
                incrementSS(hours);
                labourDollars += yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case DS:
                incrementDS(hours);
                labourDollars += yearPayRate.get(pLevel).doubleValue() * hours;
                break;
        }
    }
}
