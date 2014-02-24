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
public class WorkPackageAssignment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    private String workPackageNumber;

    @Column
    private int userId;

    @Column
    private boolean isResponsibleEngineer;

    @Temporal(TemporalType.DATE)
    private Date activateDate;

    @Temporal(TemporalType.DATE)
    private Date deactivateDate;

    public boolean isResponsibleEngineer() {
        return isResponsibleEngineer;
    }

    public void setResponsibleEngineer(boolean isResponsibleEngineer) {
        this.isResponsibleEngineer = isResponsibleEngineer;
    }

    public Date getActivateDate() {
        return activateDate;
    }

    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    public Date getDeactivateDate() {
        return deactivateDate;
    }

    public void setDeactivateDate(Date deactivateDate) {
        this.deactivateDate = deactivateDate;
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

    public String getWorkPackageNumber() {
        return workPackageNumber;
    }

    public void setWorkPackageNumber(String workPackageNumber) {
        this.workPackageNumber = workPackageNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getIsResponsibleEngineer() {
        return this.isResponsibleEngineer;
    }

    public void setIsResponsibleEngineer(final boolean isResponsibleEngineer) {
        this.isResponsibleEngineer = isResponsibleEngineer;
    }

    public WorkPackageAssignment(String workPackageNumber, int userId, boolean isResponsibleEngineer, Date activateDate, Date deactivateDate) {
        this.version = version;
        this.workPackageNumber = workPackageNumber;
        this.userId = userId;
        this.isResponsibleEngineer = isResponsibleEngineer;
        this.activateDate = activateDate;
        this.deactivateDate = deactivateDate;
    }

    public WorkPackageAssignment() {

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
            return id.equals(((WorkPackageAssignment) that).id);
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
        result += "isResponsibleEngineer: " + isResponsibleEngineer;
        return result;
    }
}

/**
 {
 "id": 11,
 "version": 0,
 "workPackageNumber": "123400",
 "userId": 1,
 "isResponsibleEngineer": false,
 "activateDate": "2014-02-24",
 "deactivateDate": "2014-02-24",
 "responsibleEngineer": false
 }
 */