package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.util.Currency;
import java.lang.Override;

import ca.bcit.infosys.comp4911.domain.Effort;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;

@Entity
public class WorkPackage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;
    @Version
    @Column(name = "version")
    private int version = 0;

    @ManyToOne
    private Project project;

    @Column
    private int workPackageNumber;

    @Column
    private String workPackageName;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    private Date completeDate;

    private String progressStatus;

    private double budget;

    private String purpose;

    private String inputs;

    private String activities;

    private String outputs;

    @Column
    private Currency ActualCostWorkPerformedInDollars;

    @Column
    private int ActualCostWorkPerformedInPD;

    @Column
    private Currency BudgetedCostWorkPerformedInDollars;

    @Column
    private int BudgetedCostWorkPerfomedInPD;

    @Column
    private Currency BudgetedCostWorkScheduledInDollars;

    @OneToMany
    private Set<Effort> BudgetCostWorkScheduledInPD = new HashSet<Effort>();

    @Column
    private Currency estimateAtCompletionInDollars;

    @Column
    private int estimateAtCompletionInPD;

    @Column
    private Date estimatedCompleteDate;

    @Column
    private Currency estimateToCompletionInDollars;

    @Column
    private int estimateToCompletionInPD;

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getOutputs() {
        return outputs;
    }

    public void setOutputs(String outputs) {
        this.outputs = outputs;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        if (id != null) {
            return id.equals(((WorkPackage) that).id);
        }
        return super.equals(that);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(final Project projectID) {
        this.project = projectID;
    }

    public int getWorkPackageNumber() {
        return this.workPackageNumber;
    }

    public void setWorkPackageNumber(final int workPackageNumber) {
        this.workPackageNumber = workPackageNumber;
    }

    public String getWorkPackageName() {
        return this.workPackageName;
    }

    public void setWorkPackageName(final String workPackageName) {
        this.workPackageName = workPackageName;
    }

    public Date getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getCompleteDate() {
        return this.completeDate;
    }

    public void setCompleteDate(final Date completeDate) {
        this.completeDate = completeDate;
    }

    public Currency getActualCostWorkPerformedInDollars() {
        return this.ActualCostWorkPerformedInDollars;
    }

    public void setActualCostWorkPerformedInDollars(
            final Currency ActualCostWorkPerformedInDollars) {
        this.ActualCostWorkPerformedInDollars = ActualCostWorkPerformedInDollars;
    }

    public int getActualCostWorkPerformedInPD() {
        return this.ActualCostWorkPerformedInPD;
    }

    public void setActualCostWorkPerformedInPD(final int ActualCostWorkPerformedInPD) {
        this.ActualCostWorkPerformedInPD = ActualCostWorkPerformedInPD;
    }

    public Currency getBudgetedCostWorkPerformedInDollars() {
        return this.BudgetedCostWorkPerformedInDollars;
    }

    public void setBudgetedCostWorkPerformedInDollars(
            final Currency BudgetedCostWorkPerformedInDollars) {
        this.BudgetedCostWorkPerformedInDollars = BudgetedCostWorkPerformedInDollars;
    }

    public int getBudgetedCostWorkPerfomedInPD() {
        return this.BudgetedCostWorkPerfomedInPD;
    }

    public void setBudgetedCostWorkPerfomedInPD(
            final int BudgetedCostWorkPerfomedInPD) {
        this.BudgetedCostWorkPerfomedInPD = BudgetedCostWorkPerfomedInPD;
    }

    public Currency getBudgetedCostWorkScheduledInDollars() {
        return this.BudgetedCostWorkScheduledInDollars;
    }

    public void setBudgetedCostWorkScheduledInDollars(
            final Currency BudgetedCostWorkScheduledInDollars) {
        this.BudgetedCostWorkScheduledInDollars = BudgetedCostWorkScheduledInDollars;
    }

    public Set<Effort> getBudgetCostWorkScheduledInPD() {
        return this.BudgetCostWorkScheduledInPD;
    }

    public void setBudgetCostWorkScheduledInPD(
            final Set<Effort> BudgetCostWorkScheduledInPD) {
        this.BudgetCostWorkScheduledInPD = BudgetCostWorkScheduledInPD;
    }

    public Currency getEstimateAtCompletionInDollars() {
        return this.estimateAtCompletionInDollars;
    }

    public void setEstimateAtCompletionInDollars(
            final Currency estimateAtCompletionInDollars) {
        this.estimateAtCompletionInDollars = estimateAtCompletionInDollars;
    }

    public int getEstimateAtCompletionInPD() {
        return this.estimateAtCompletionInPD;
    }

    public void setEstimateAtCompletionInPD(final int estimateAtCompletionInPD) {
        this.estimateAtCompletionInPD = estimateAtCompletionInPD;
    }

    public Date getEstimatedCompleteDate() {
        return this.estimatedCompleteDate;
    }

    public void setEstimatedCompleteDate(final Date estimatedCompleteDate) {
        this.estimatedCompleteDate = estimatedCompleteDate;
    }

    public Currency getEstimateToCompletionInDollars() {
        return this.estimateToCompletionInDollars;
    }

    public void setEstimateToCompletionInDollars(
            final Currency estimateToCompletionInDollars) {
        this.estimateToCompletionInDollars = estimateToCompletionInDollars;
    }

    public int getEstimateToCompletionInPD() {
        return this.estimateToCompletionInPD;
    }

    public void setEstimateToCompletionInPD(final int estimateToCompletionInPD) {
        this.estimateToCompletionInPD = estimateToCompletionInPD;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        result += "workPackageNumber: " + workPackageNumber;
        if (workPackageName != null && !workPackageName.trim().isEmpty())
            result += ", workPackageName: " + workPackageName;
        if (progressStatus != null && !progressStatus.trim().isEmpty())
            result += ", progressStatus: " + progressStatus;
        result += ", budget: " + budget;
        if (purpose != null && !purpose.trim().isEmpty())
            result += ", purpose: " + purpose;
        if (inputs != null && !inputs.trim().isEmpty())
            result += ", inputs: " + inputs;
        if (activities != null && !activities.trim().isEmpty())
            result += ", activities: " + activities;
        if (outputs != null && !outputs.trim().isEmpty())
            result += ", outputs: " + outputs;
        result += ", ActualCostWorkPerformedInPD: " + ActualCostWorkPerformedInPD;
        result += ", BudgetedCostWorkPerfomedInPD: " + BudgetedCostWorkPerfomedInPD;
        result += ", estimateAtCompletionInPD: " + estimateAtCompletionInPD;
        result += ", estimateToCompletionInPD: " + estimateToCompletionInPD;
        return result;
    }
}