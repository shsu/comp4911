package ca.bcit.infosys.comp4911.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class WorkPackage implements Serializable
{
   @Id
   @Column(updatable = false, nullable = false)
   private String workPackageNumber;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String workPackageName;

   @Column
   private int projectNumber;

   @Temporal(TemporalType.DATE)
   private Date issueDate;

   @Temporal(TemporalType.DATE)
   private Date completeDate;

   @Temporal(TemporalType.DATE)
   private Date startDate;

   @Temporal(TemporalType.DATE)
   private Date estimatedEndDate;

   @Column
   private String purpose;

   @Column
   private String description;

   @Column
   private String progressStatus;

   @JsonIgnore
   @OneToMany
   private Set<Effort> budgetCostWorkScheduledInPD = new HashSet<Effort>();

   public String getWorkPackageNumber()
   {
      return workPackageNumber;
   }

   public void setWorkPackageNumber(String workPackageNumber)
   {
      this.workPackageNumber = workPackageNumber;
   }

   public int getVersion()
   {
      return version;
   }

   public void setVersion(int version)
   {
      this.version = version;
   }

   public String getWorkPackageName()
   {
      return workPackageName;
   }

   public void setWorkPackageName(String workPackageName)
   {
      this.workPackageName = workPackageName;
   }

   public Date getIssueDate()
   {
      return issueDate;
   }

   public void setIssueDate(Date issueDate)
   {
      this.issueDate = issueDate;
   }

   public Date getCompleteDate()
   {
      return completeDate;
   }

   public void setCompleteDate(Date completeDate)
   {
      this.completeDate = completeDate;
   }

   public Date getStartDate()
   {
      return startDate;
   }

   public void setStartDate(Date startDate)
   {
      this.startDate = startDate;
   }

   public String getProgressStatus()
   {
      return progressStatus;
   }

   public void setProgressStatus(String progressStatus)
   {
      this.progressStatus = progressStatus;
   }

   public String getPurpose()
   {
      return purpose;
   }

   public void setPurpose(String purpose)
   {
      this.purpose = purpose;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public Set<Effort> getBudgetCostWorkScheduledInPD()
   {
      return budgetCostWorkScheduledInPD;
   }

   public void setBudgetCostWorkScheduledInPD(Set<Effort> budgetCostWorkScheduledInPD)
   {
      this.budgetCostWorkScheduledInPD = budgetCostWorkScheduledInPD;
   }

   public Date getEstimatedEndDate()
   {
      return estimatedEndDate;
   }

   public void setEstimatedEndDate(Date estimatedEndDate)
   {
      this.estimatedEndDate = estimatedEndDate;
   }

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      WorkPackage that = (WorkPackage) o;

      if (!workPackageNumber.equals(that.workPackageNumber))
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      return workPackageNumber.hashCode();
   }

   public WorkPackage(String workPackageNumber, String workPackageName, int projectNumber, Date issueDate, Date completeDate, Date startDate, String progressStatus, String purpose, String description, Set<Effort> budgetCostWorkScheduledInPD, int estimateToCompletionInPD)
   {
      this.workPackageNumber = workPackageNumber;
      this.workPackageName = workPackageName;
      this.projectNumber = projectNumber;
      this.issueDate = issueDate;
      this.completeDate = completeDate;
      this.startDate = startDate;
      this.progressStatus = progressStatus;
      this.purpose = purpose;
      this.description = description;
      this.budgetCostWorkScheduledInPD = budgetCostWorkScheduledInPD;
   }

   public WorkPackage()
   {
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (workPackageNumber != null && !workPackageNumber.trim().isEmpty())
         result += "workPackageNumber: " + workPackageNumber;
      if (workPackageName != null && !workPackageName.trim().isEmpty())
         result += ", workPackageName: " + workPackageName;
      if (purpose != null && !purpose.trim().isEmpty())
         result += ", purpose: " + purpose;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (progressStatus != null && !progressStatus.trim().isEmpty())
         result += ", progressStatus: " + progressStatus;
      return result;
   }
}

/**
 {
 "workPackageNumber": "123400",
 "version": 0,
 "workPackageName": "WorkPackageName0",
 "project": {
 "projectNumber": "12340",
 "version": 0,
 "projectName": "Project0",
 "issueDate": "2014-02-24",
 "completeDate": "2014-02-24",
 "clientRate": 12.5,
 "allocatedBudget": 1000,
 "unAllocatedBudget": 1000
 },
 "issueDate": "2014-02-24",
 "completeDate": "2014-02-24",
 "startDate": "2014-02-24",
 "estimatedEndDate": null,
 "purpose": "description0",
 "description": "inputs0",
 "inputs": "outputs0",
 "activities": "activities0",
 "outputs": "progressStatus0",
 "progressStatus": "purpose0",
 "estimateToCompletionInPD": 100
 }
 */
