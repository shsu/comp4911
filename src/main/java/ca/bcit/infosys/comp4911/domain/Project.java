package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.Override;
import java.util.Currency;

import ca.bcit.infosys.comp4911.domain.WorkPackage;

import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;

import ca.bcit.infosys.comp4911.domain.Effort;

@Entity
public class Project implements Serializable {

    /** Actual id given to it by the HR who creates it */
    @Id
    @Column(updatable = false, nullable = false)
    private String projectNumber;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column(nullable = false)
    private String projectName;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    private Date completeDate;

    @OneToMany(mappedBy = "project")
    private Set<WorkPackage> workPackages = new HashSet<WorkPackage>();

    @Column
    private BigDecimal clientRate;

    @Column
    private BigDecimal UnAllocatedBudget;

    @Column
    private BigDecimal AllocatedBudget;

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public Set<WorkPackage> getWorkPackages() {
        return workPackages;
    }

    public void setWorkPackages(Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
    }

    public BigDecimal getUnAllocatedBudget() {
        return UnAllocatedBudget;
    }

    public void setUnAllocatedBudget(BigDecimal unAllocatedBudget) {
        UnAllocatedBudget = unAllocatedBudget;
    }

    public BigDecimal getAllocatedBudget() {
        return AllocatedBudget;
    }

    public void setAllocatedBudget(BigDecimal allocatedBudget) {
        AllocatedBudget = allocatedBudget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (projectNumber != null ? !projectNumber.equals(project.projectNumber) : project.projectNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return projectNumber != null ? projectNumber.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectNumber='" + projectNumber + '\'' +
                ", version=" + version +
                ", projectName='" + projectName + '\'' +
                ", issueDate=" + issueDate +
                ", completeDate=" + completeDate +
                ", workPackages=" + workPackages +
                ", clientRate=" + clientRate +
                ", UnAllocatedBudget=" + UnAllocatedBudget +
                ", AllocatedBudget=" + AllocatedBudget +
                '}';
    }
}