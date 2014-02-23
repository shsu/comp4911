package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
public class TimesheetRow implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    private String projectNumber;

    @Column
    private String workPackageNumber;

    /**
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Timesheet timesheet;
     */

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

    public TimesheetRow(String projectNumber, String workPackageNumber, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, int sunday, String note) {
        this.projectNumber = projectNumber;
        this.workPackageNumber = workPackageNumber;
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
}