package ca.bcit.infosys.comp4911.helper;


import ca.bcit.infosys.comp4911.access.PayRateDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;

/**
 * Created by craigleclair on 2014-03-29.
 */
public class ProjectBudgetReport {

    ReportHelperRow initialBudget;

    ReportHelperRow currentSpending;

    ReportHelperRow expectedBudget;

    public ProjectBudgetReport(WorkPackageDao workPackageDao, PayRateDao payRateDao){
        initialBudget = new ReportHelperRow(workPackageDao, payRateDao);
        currentSpending = new ReportHelperRow(workPackageDao, payRateDao);
        expectedBudget = new ReportHelperRow(workPackageDao, payRateDao);
    }

    public ReportHelperRow getCurrentSpending() {
        return currentSpending;
    }

    public void setCurrentSpending(ReportHelperRow currentSpending) {
        this.currentSpending = currentSpending;
    }

    public ReportHelperRow getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(ReportHelperRow initialBudget) {
        this.initialBudget = initialBudget;
    }

    public ReportHelperRow getExpectedBudget() {
        return expectedBudget;
    }

    public void setExpectedBudget(ReportHelperRow expectedBudget) {
        this.expectedBudget = expectedBudget;
    }
}
