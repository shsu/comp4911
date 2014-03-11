package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.lang.Override;

@Entity
public class ProjectAssignment implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Integer id = null;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Min(0)
   private int userId;

   @Column
   private boolean isProjectManager;

   @Column
   @Size(min=5)
   private String projectNumber;

   public Integer getId()
   {
      return this.id;
   }

   public void setId(final Integer id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   public boolean isProjectManager()
   {
      return this.isProjectManager;
   }

   public void setProjectManager(boolean isProjectManager)
   {
      this.isProjectManager = isProjectManager;
   }

   public int getUserId()
   {
      return userId;
   }

   public void setUserId(int userId)
   {
      this.userId = userId;
   }

   public ProjectAssignment(String projectNumber, int userId, boolean isProjectManager)
   {
      this.projectNumber = projectNumber;
      this.userId = userId;
      this.isProjectManager = isProjectManager;
   }

   public ProjectAssignment()
   {
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((ProjectAssignment) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getProjectNumber()
   {
      return this.projectNumber;
   }

   public void setProjectNumber(final String projectNumber)
   {
      this.projectNumber = projectNumber;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "userId: " + userId;
      result += ", isProjectManager: " + isProjectManager;
      if (projectNumber != null && !projectNumber.trim().isEmpty())
         result += ", projectNumber: " + projectNumber;
      return result;
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
