package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class WorkPackageStatusReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    private int weekNumber;

    @Column
    private int year;

    @Column
    private String workPackageId;

    @Temporal(TemporalType.DATE)
    private Date reportDate;

    @Column
    private String comment;

    @Column
    private String workAccomplished;

    @Column
    private String problemEncountered;

    @Column
    private String workPlanned;

    @OneToMany
    private Set<Effort> estimatedWorkRemainingInPD = new HashSet<Effort>();

    @Column
    private String problemAnticipated;

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWorkAccomplished() {
        return workAccomplished;
    }

    public void setWorkAccomplished(String workAccomplished) {
        this.workAccomplished = workAccomplished;
    }

    public String getProblemEncountered() {
        return problemEncountered;
    }

    public void setProblemEncountered(String problemEncountered) {
        this.problemEncountered = problemEncountered;
    }

    public String getWorkPlanned() {
        return workPlanned;
    }

    public void setWorkPlanned(String workPlanned) {
        this.workPlanned = workPlanned;
    }

    public String getProblemAnticipated() {
        return problemAnticipated;
    }

    public void setProblemAnticipated(String problemAnticipated) {
        this.problemAnticipated = problemAnticipated;
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

    public int getWeekNumber() {
        return this.weekNumber;
    }

    public void setWeekNumber(final int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public String getWorkPackageId() {
        return workPackageId;
    }

    public void setWorkPackageId(String workPackageId) {
        this.workPackageId = workPackageId;
    }

    public Set<Effort> getEstimatedWorkRemainingInPD() {
        return this.estimatedWorkRemainingInPD;
    }

    public void setEstimatedWorkRemainingInPD(
            final Set<Effort> estimatedWorkRemainingInPD) {
        this.estimatedWorkRemainingInPD = estimatedWorkRemainingInPD;
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
            return id.equals(((WorkPackageStatusReport) that).id);
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

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        result += "weekNumber: " + weekNumber;
        result += ", year: " + year;
        return result;
    }

}