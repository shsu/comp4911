package ca.bcit.infosys.comp4911.helper;

import ca.bcit.infosys.comp4911.access.PayRateDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.domain.*;
import org.joda.time.DateTime;

import javax.ejb.EJB;
import java.math.BigDecimal;
import java.util.*;

/**
 * A project manager needs to see the most recent WPs associated with a project, and how much work has been
 * done for each work package within the project.
 * Created by craigleclair on 3/14/2014.
 */
public class ReportHelperRow {

    public HashMap<Integer, HashMap<PLevel, BigDecimal>> yearPLevelRateHashMap;

    private WorkPackageDao workPackageDao;
    final private static double CONVERT_FROM_TENTHS = 0.1;
    private HashMap<PLevel, Integer> pLevels;
    private double labourDollars;
    private String wpNumber;
    private String wpDescription;

    public ReportHelperRow(WorkPackageDao workPackageDao,
                           HashMap<Integer, HashMap<PLevel, BigDecimal>> yearPLevelRateHashMap) {
        this.workPackageDao = workPackageDao;
        this.yearPLevelRateHashMap = yearPLevelRateHashMap;
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
        HashMap<String, Date> wpNumberDateHash  =
                workPackageDao.getWPNumberDateHash(timesheetRowList.get(0).getProjectNumber());
        while(timesheetRowIterator.hasNext())
        {
            tsr = timesheetRowIterator.next();
            increasePLevel(yearPLevelRateHashMap.get(getYearInt(wpNumberDateHash.get(tsr.getWorkPackageNumber()))),
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
            increasePLevel(yearPLevelRateHashMap.get(getYearInt(date)),
                    effort.getpLevel(), effort.getPersonDays());

        }
    }

    public int getYearInt(Date date){
        DateTime dateTime = new DateTime(date.toString());
        return dateTime.getYear();
    }

    public void increasePLevel(HashMap<PLevel, BigDecimal> yearPayRate, PLevel pLevel, int hours){

        switch(pLevel){
            case P1:
                incrementP1(hours);
                labourDollars += CONVERT_FROM_TENTHS * yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P2:
                incrementP2(hours);
                labourDollars += CONVERT_FROM_TENTHS * yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P3:
                incrementP3(hours);
                labourDollars += CONVERT_FROM_TENTHS * yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P4:
                incrementP4(hours);
                labourDollars += CONVERT_FROM_TENTHS * yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case P5:
                incrementP5(hours);
                labourDollars += CONVERT_FROM_TENTHS * yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case SS:
                incrementSS(hours);
                labourDollars += CONVERT_FROM_TENTHS * yearPayRate.get(pLevel).doubleValue() * hours;
                break;
            case DS:
                incrementDS(hours);
                labourDollars += CONVERT_FROM_TENTHS * yearPayRate.get(pLevel).doubleValue() * hours;
                break;
        }
    }

    public static ArrayList<WorkPackageStatusReport> getSingleWPSRPerWP(List<WorkPackageStatusReport> allWPSR) {
        /**
         * HashMap is used to check for double WPSRs. Check for double in order to only take the most
         * recent WPSR.
         */
        HashMap<String, Boolean> singleWPSRPerWP = new HashMap<String,
                Boolean>();
        ArrayList<WorkPackageStatusReport> mostRecentWPSR = new ArrayList<WorkPackageStatusReport>();
        WorkPackageStatusReport currentWPSR;
        Iterator<WorkPackageStatusReport> wpsrIterator = allWPSR.listIterator();
        while(wpsrIterator.hasNext()){
            currentWPSR = wpsrIterator.next();
            if(singleWPSRPerWP.containsKey(currentWPSR.getWorkPackageNumber())){
                continue;
            }
            else {
                singleWPSRPerWP.put(currentWPSR.getWorkPackageNumber(), true);
                mostRecentWPSR.add(currentWPSR);

            }

        }
        return mostRecentWPSR;
    }
}
