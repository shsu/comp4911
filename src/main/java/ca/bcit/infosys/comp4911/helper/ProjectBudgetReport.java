package ca.bcit.infosys.comp4911.helper;

import ca.bcit.infosys.comp4911.domain.PLevel;

import java.util.HashMap;

/**
 * Created by craigleclair on 2014-03-29.
 */
public class ProjectBudgetReport {

    ReportHelperRow initialBudget;

    ReportHelperRow currentSpending;

    ReportHelperRow expectedBudget;

    public ProjectBudgetReport(){
        initialBudget = new ReportHelperRow();
        currentSpending = new ReportHelperRow();
        expectedBudget = new ReportHelperRow();
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
