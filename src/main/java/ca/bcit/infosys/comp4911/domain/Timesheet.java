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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Timesheet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TIMESHEET_ID", updatable = false, nullable = false)
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
    private int weekNumber;

    @Column
    private int year;

    @Column
    private int flexTime;

    @Column
    private int overTime;

    @Column
    private boolean isApproved;

    @Column
    private boolean isSigned;

    @Column
    private int userId;

    public List<TimesheetRow> getTimesheetRows() {
        return timesheetRows;
    }

    public void setTimesheetRows(List<TimesheetRow> timesheetRows) {
        this.timesheetRows = timesheetRows;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean isSigned) {
        this.isSigned = isSigned;
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

    public int getFlexTime() {
        return flexTime;
    }

    public void setFlexTime(int flexTime) {
        this.flexTime = flexTime;
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

    public boolean getIsApproved() {
        return this.isApproved;
    }

    public void setIsApproved(final boolean isApproved) {
        this.isApproved = isApproved;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Timesheet(int userId, int weekNumber, int year, int flexTime, int overTime, boolean isApproved, boolean isSigned) {
        this.userId = userId;
        this.weekNumber = weekNumber;
        this.year = year;
        this.flexTime = flexTime;
        this.overTime = overTime;
        this.isApproved = isApproved;
        this.isSigned = isSigned;
        timesheetRows = new ArrayList<TimesheetRow>();
    }

    public Timesheet(int userId, List<TimesheetRow> rows, int weekNumber, int year, int flexTime, int overTime, boolean isApproved, boolean isSigned) {
        this.userId = userId;
        this.weekNumber = weekNumber;
        this.year = year;
        this.flexTime = flexTime;
        this.overTime = overTime;
        this.isApproved = isApproved;
        this.isSigned = isSigned;
        timesheetRows = rows;
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
        result += ", flexTime: " + flexTime;
        result += ", overTime: " + overTime;
        result += ", isApproved: " + isApproved;
        result += ", isSigned: " + isSigned;
        return result;
    }
}