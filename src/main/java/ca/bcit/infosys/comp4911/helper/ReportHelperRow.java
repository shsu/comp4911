package ca.bcit.infosys.comp4911.helper;

import ca.bcit.infosys.comp4911.domain.Effort;
import ca.bcit.infosys.comp4911.domain.PLevel;
import ca.bcit.infosys.comp4911.domain.WorkPackageStatusReport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A project manager needs to see the most recent WPs associated with a project, and how much work has been
 * done for each work package within the project.
 * Created by craigleclair on 3/14/2014.
 */
public class ReportHelperRow {
    private HashMap<PLevel, Integer> pLevels;
    private double total;
    private double labourDollars;
    private String wpNumber;
    private String wpDescription;

    public ReportHelperRow() {
        pLevels = new HashMap<PLevel, Integer>();
        pLevels.put(PLevel.P1, 0);
        pLevels.put(PLevel.P2, 0);
        pLevels.put(PLevel.P3, 0);
        pLevels.put(PLevel.P4, 0);
        pLevels.put(PLevel.P5, 0);
        pLevels.put(PLevel.SS, 0);
        pLevels.put(PLevel.DS, 0);
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public void increasePLevel(PLevel pLevel, int hours){

        switch(pLevel){
            case P1:
                incrementP1(hours);
                break;
            case P2:
                incrementP2(hours);
                break;
            case P3:
                incrementP3(hours);
                break;
            case P4:
                incrementP4(hours);
                break;
            case P5:
                incrementP5(hours);
                break;
            case SS:
                incrementSS(hours);
                break;
            case DS:
                incrementDS(hours);
                break;
        }
    }

    /**
     * This method is used to access the current PayRate for the work packages year. Once the PayRate for the given
     * year has been found, the rate for each Pay Level is used to calculate the total Labour dollars. It is then
     * stored in the ReportHelperRow.
     * @param yearPayRate
     */
    public void calculateTotalLabourDollars(HashMap<PLevel, BigDecimal> yearPayRate){
        double totalLabourDollars = 0;
        totalLabourDollars += yearPayRate.get(PLevel.DS).doubleValue() * getpLevels().get(PLevel.DS);
        totalLabourDollars += yearPayRate.get(PLevel.P1).doubleValue() * getpLevels().get(PLevel.P1);
        totalLabourDollars += yearPayRate.get(PLevel.P2).doubleValue() * getpLevels().get(PLevel.P2);
        totalLabourDollars += yearPayRate.get(PLevel.P3).doubleValue() * getpLevels().get(PLevel.P3);
        totalLabourDollars += yearPayRate.get(PLevel.P4).doubleValue() * getpLevels().get(PLevel.P4);
        totalLabourDollars += yearPayRate.get(PLevel.P5).doubleValue() * getpLevels().get(PLevel.P5);
        totalLabourDollars += yearPayRate.get(PLevel.SS).doubleValue() * getpLevels().get(PLevel.SS);
        setLabourDollars(totalLabourDollars);
    }

    public void calculateProjectExpectedPLevelTotals(ArrayList<WorkPackageStatusReport> oneStatusReportPerWP){
        int length = oneStatusReportPerWP.size();
        ArrayList<Effort> effort;
        for(int i = 0; i < length; i++){
            effort = (ArrayList)oneStatusReportPerWP.get(i).getEstimatedWorkRemainingInPD();
            for(int j = 0; j < effort.size(); j++){
                increasePLevel(effort.get(j).getpLevel(), effort.get(j).getPersonDays());
            }
        }
    }
}
