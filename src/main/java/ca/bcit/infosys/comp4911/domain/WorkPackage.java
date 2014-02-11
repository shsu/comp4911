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

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        result += "workPackageNumber: " + workPackageNumber;
        if (workPackageName != null && !workPackageName.trim().isEmpty())
            result += ", workPackageName: " + workPackageName;
        return result;
    }
}