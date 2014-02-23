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
    @Column(updatable=false, nullable=false)
    private int workPackageNumber;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    private String workPackageName;

    @ManyToOne
    private Project project;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    private Date completeDate;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column
    private String progressStatus;

    @Column
    private String purpose;

    @Column
    private String description;

    @Column
    private String inputs;

    @Column
    private String activities;

    @Column
    private String outputs;

    @OneToMany
    private Set<Effort> BudgetCostWorkScheduledInPD = new HashSet<Effort>();


    @Column
    private int estimateToCompletionInPD;

    public int getWorkPackageNumber() {
        return workPackageNumber;
    }

    public void setWorkPackageNumber(int workPackageNumber) {
        this.workPackageNumber = workPackageNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getWorkPackageName() {
        return workPackageName;
    }

    public void setWorkPackageName(String workPackageName) {
        this.workPackageName = workPackageName;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Effort> getBudgetCostWorkScheduledInPD() {
        return BudgetCostWorkScheduledInPD;
    }

    public void setBudgetCostWorkScheduledInPD(Set<Effort> budgetCostWorkScheduledInPD) {
        BudgetCostWorkScheduledInPD = budgetCostWorkScheduledInPD;
    }

    public int getEstimateToCompletionInPD() {
        return estimateToCompletionInPD;
    }

    public void setEstimateToCompletionInPD(int estimateToCompletionInPD) {
        this.estimateToCompletionInPD = estimateToCompletionInPD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkPackage that = (WorkPackage) o;

        if (version != that.version) return false;
        if (workPackageNumber != that.workPackageNumber) return false;
        if (workPackageName != null ? !workPackageName.equals(that.workPackageName) : that.workPackageName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = workPackageNumber;
        result = 31 * result + version;
        result = 31 * result + (workPackageName != null ? workPackageName.hashCode() : 0);
        return result;
    }

    public WorkPackage(int workPackageNumber, int version, String workPackageName, Project project, Date issueDate, Date completeDate, Date startDate, String progressStatus, String purpose, String description, String inputs, String activities, String outputs, Set<Effort> budgetCostWorkScheduledInPD, int estimateToCompletionInPD) {
        this.workPackageNumber = workPackageNumber;
        this.version = version;
        this.workPackageName = workPackageName;
        this.project = project;
        this.issueDate = issueDate;
        this.completeDate = completeDate;
        this.startDate = startDate;
        this.progressStatus = progressStatus;
        this.purpose = purpose;
        this.description = description;
        this.inputs = inputs;
        this.activities = activities;
        this.outputs = outputs;
        BudgetCostWorkScheduledInPD = budgetCostWorkScheduledInPD;
        this.estimateToCompletionInPD = estimateToCompletionInPD;
    }

    public WorkPackage()
    {
    }
}