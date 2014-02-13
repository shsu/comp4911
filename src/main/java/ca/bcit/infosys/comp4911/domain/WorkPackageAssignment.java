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

    @ManyToOne
    private WorkPackage workPackage;

    @ManyToOne
    private User user;

    @Column
    private boolean isResponsibleEngineer;

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

    @Temporal(TemporalType.DATE)
    private Date activateDate;

    @Temporal(TemporalType.DATE)
    private Date deactivateDate;

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

    public WorkPackage getWorkPackage() {
        return this.workPackage;
    }

    public void setWorkPackage(final WorkPackage workPackage) {
        this.workPackage = workPackage;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public boolean getIsResponsibleEngineer() {
        return this.isResponsibleEngineer;
    }

    public void setIsResponsibleEngineer(final boolean isResponsibleEngineer) {
        this.isResponsibleEngineer = isResponsibleEngineer;
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        result += "isResponsibleEngineer: " + isResponsibleEngineer;
        return result;
    }
}