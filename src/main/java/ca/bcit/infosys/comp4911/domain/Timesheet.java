package ca.bcit.infosys.comp4911.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Min;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Timesheet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TIMESHEET_ID", updatable = false, nullable = false)
    @Min(0) 
    private Integer id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="TIMESHEET_TIMESHEET_ROW",
            joinColumns = { @JoinColumn(name="TIMESHEET_ID")},
            inverseJoinColumns = { @JoinColumn(name="ROW_ID")})
    private List<TimesheetRow> timesheetRows;

    @Column
    @Min(0)
    private int weekNumber;

    @Column
    @Min(0)
    private int year;

    @Column
    @Min(0)
    private int overTime;

    @Column
    private boolean approved;

    @Column
    private boolean signed;

    @Column
    @Min(0)
    private int userId;

    @Column
    private boolean pending;

    public List<TimesheetRow> getTimesheetRows() {
        return timesheetRows;
    }

    public void setTimesheetRows(List<TimesheetRow> timesheetRows) {
        this.timesheetRows = timesheetRows;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) { this.approved = approved; }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
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

    public int getOverTime() {
        return overTime;
    }

    public void setOverTime(int overTime) {
        this.overTime = overTime;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isPending() { return pending; }

    public void setPending(boolean pending) { this.pending = pending; }

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
            return id.equals(((Timesheet) that).id);
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

    public Timesheet(int userId, List<TimesheetRow> rows, int weekNumber, int year, int overTime, boolean isApproved, boolean isSigned, boolean pending) {
        this.userId = userId;
        this.weekNumber = weekNumber;
        this.year = year;
        this.overTime = overTime;
        this.approved = isApproved;
        this.signed = isSigned;
        this.timesheetRows = rows;
        this.pending = pending;
    }

    public Timesheet(int userId, int weekNumber, int year, int overTime, boolean isApproved, boolean isSigned) {
        this.userId = userId;
        this.weekNumber = weekNumber;
        this.year = year;
        this.overTime = overTime;
        this.approved = isApproved;
        this.signed = isSigned;
        timesheetRows = new ArrayList<TimesheetRow>();
    }

    public Timesheet() {
        timesheetRows = new ArrayList<TimesheetRow>();
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        result += ", weekNumber: " + weekNumber;
        result += ", year: " + year;
        result += ", overTime: " + overTime;
        result += ", approved: " + approved;
        result += ", signed: " + signed;
        return result;
    }
}