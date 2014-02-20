package ca.bcit.infosys.comp4911.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.lang.Override;

import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.WorkPackage;

@Entity
public class TimesheetRow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;

    @Column
    private int projectId;

    @Column
    private int workPackageId;

    @Version
    @Column(name = "version")
    private int version = 0;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Timesheet timesheet;

    // All these values are now in tenths
    @Column
    private int monday;

    @Column
    private int tuesday;

    @Column
    private int wednesday;

    @Column
    private int thursday;

    @Column
    private int friday;

    @Column
    private int saturday;

    @Column
    private int sunday;

    @Column
    private String note;

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

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWednesday() {
        return wednesday;
    }

    public void setWednesday(int wednesday) {
        this.wednesday = wednesday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
            return id.equals(((TimesheetRow) that).id);
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

    public TimesheetRow(int projectId, int workPackageId, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday, String note) {
        this.projectId = projectId;
        this.workPackageId = workPackageId;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.note = note;
    }

    public TimesheetRow() {
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        result += ", monday: " + monday;
        result += ", tuesday: " + tuesday;
        result += ", wednesday: " + wednesday;
        result += ", thursday: " + thursday;
        result += ", friday: " + friday;
        result += ", saturday: " + saturday;
        result += ", sunday: " + sunday;
        if (note != null && !note.trim().isEmpty())
            result += ", note: " + note;
        return result;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getWorkPackageId() {
        return workPackageId;
    }

    public void setWorkPackageId(int workPackageId) {
        this.workPackageId = workPackageId;
    }
}