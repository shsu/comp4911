package ca.bcit.infosys.comp4911.helper;

/**
 * Created by craigleclair on 3/14/2014.
 */
public class ReportHelper {
    private Integer projectNumber;
    private String projectName;
    private ReportHelperRow[] rhRows = new ReportHelperRow[20];
    private Integer projectBudget;
    private Integer usedBudget;

    public Integer getUsedBudget() {
        return usedBudget;
    }

    public void setUsedBudget(Integer usedBudget) {
        this.usedBudget = usedBudget;
    }

    public Integer getProjectBudget() {
        return projectBudget;
    }

    public void setProjectBudget(Integer projectBudget) {
        this.projectBudget = projectBudget;
    }

    public ReportHelper(Integer projectNumber, String projectName) {
        this.projectNumber = projectNumber;
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(Integer projectNumber) {
        this.projectNumber = projectNumber;
    }

    public ReportHelperRow[] getRhRows() {
        return rhRows;
    }

    public void setRhRows(ReportHelperRow[] rhRows) {
        this.rhRows = rhRows;
    }
}
