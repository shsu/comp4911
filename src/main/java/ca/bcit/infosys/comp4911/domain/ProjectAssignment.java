package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
public class ProjectAssignment implements Serializable {

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
    private int userId;

    @Column
    private boolean isProjectManager;

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

    public boolean isProjectManager() {
        return this.isProjectManager;
    }

    public void setProjectManager(boolean isProjectManager) {
        this.isProjectManager = isProjectManager;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ProjectAssignment(String projectNumber,  int userId, boolean isProjectManager) {
        this.projectNumber = projectNumber;
        this.userId = userId;
        this.isProjectManager = isProjectManager;
    }

    public ProjectAssignment()
    {
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        if (id != null)
            result += "id: " + id;
        return result;
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
            return id.equals(((ProjectAssignment) that).id);
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
}

/**
 {
 "projectNumber": "12340",
 "user": {
 "id": 2,
 "version": 0,
 "username": "username1",
 "password": "$2a$10$1SmB5/Trt0TrBRBGAlIdAuYAV49lga.bGh649X66NOSO2oTlRe0Y.",
 "firstName": "firstName1",
 "lastName": "lastName1",
 "email": "employee1@example.com",
 "startDate": "2014-02-24",
 "status": "status1",
 "defaultTimesheet": null,
 "payLevel": null,
 "supervisor": null,
 "timesheetApprover": null,
 "paidHoursPerWeek": 40,
 "totalFlexTime": 1,
 "totalOvertime": 1,
 "vacationDays": 1,
 "hr": false
 },
 "projectManager": false
 }
 */