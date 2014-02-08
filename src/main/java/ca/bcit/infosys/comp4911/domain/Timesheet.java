package ca.bcit.infosys.comp4911.domain;


import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;


/**
 * Created by Graeme on 2/8/14.
 */
@Entity
public class Timesheet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    public Integer id;

    @Column(name = "")

    public String workpackageId;

    @Column(name = "")
    public Integer workpackageNumber;

    @Column(name = "")
    public Integer year;

    @Column(name = "")
    public Double monday;

    @Column(name = "")
    public Double tuesday;

    @Column(name = "")
    public Double wednesday;

    @Column(name = "")
    public Double thursday;

    @Column(name = "")
    public Double friday;

    @Column(name = "")
    public Double saturday;

    @Column(name = "")
    public Double sunday;

    @Column(name = "")
    public String note;

    public final Integer getId() { return this.id; }

    public String getWorkpackageId() {
        return this.workpackageId;
    }

    public Integer getWorkpackageNumber() {
        return this.workpackageNumber;
    }

    public Integer getYear() {
        return this.year;
    }

    public Double getMonday() {
        return this.monday;
    }

    public Double getTuesday() {
        return this.tuesday;
    }

    public Double getWednesday() {
        return this.wednesday;
    }

    public Double getThursday() {
        return this.thursday;
    }

    public Double getFriday() {
        return this.friday;
    }

    public Double getSaturday() {
        return this.saturday;
    }

    public Double getSunday() {
        return this.sunday;
    }

    public String getNote() {
        return this.note;
    }
}
