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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Version

    @Column(name = "version")
    private int version = 0;

    /** Actual id given to it by the HR who creates it */
    @Column
    private String projectId;

    @Column
    private String projectName;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Temporal(TemporalType.DATE)
    private Date completeDate;

    @Column
    private int string;

    @OneToMany(mappedBy = "project")
    private Set<WorkPackage> workPackages = new HashSet<WorkPackage>();

    @Column
    private BigDecimal clientRate;
    @OneToMany
    private Set<Effort> BudgetCostWorkScheduledInPD = new HashSet<Effort>();

    @Column
    private BigDecimal UnAllocatedBudget;

    @Column
    private BigDecimal AllocatedBudget;

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

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
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
            return id.equals(((Project) that).id);
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

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public Date getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", version=" + version +
                ", projectName='" + projectName + '\'' +
                ", issueDate=" + issueDate +
                ", completeDate=" + completeDate +
                ", string=" + string +
                ", workPackages=" + workPackages +
                ", clientRate=" + clientRate +
                ", BudgetCostWorkScheduledInPD=" + BudgetCostWorkScheduledInPD +
                ", UnAllocatedBudget=" + UnAllocatedBudget +
                ", AllocatedBudget=" + AllocatedBudget +
                '}';
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

    public Date getCompleteDate() {
        return this.completeDate;
    }

    public void setCompleteDate(final Date completeDate) {
        this.completeDate = completeDate;
    }

    public int getString() {
        return this.string;
    }

    public void setString(final int string) {
        this.string = string;
    }


    public Set<WorkPackage> getWorkPackages() {
        return this.workPackages;
    }

    public void setWorkPackages(final Set<WorkPackage> workPackages) {
        this.workPackages = workPackages;
    }

    public Set<Effort> getBudgetCostWorkScheduledInPD() {
        return this.BudgetCostWorkScheduledInPD;
    }

    public void setBudgetCostWorkScheduledInPD(
            final Set<Effort> BudgetCostWorkScheduledInPD) {
        this.BudgetCostWorkScheduledInPD = BudgetCostWorkScheduledInPD;
    }
}