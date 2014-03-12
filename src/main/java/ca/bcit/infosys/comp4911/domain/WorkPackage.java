package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;
import java.lang.Override;

@Entity
public class WorkPackage implements Serializable
{
   @Id
   @Column(updatable = false, nullable = false)
   @Size(min = 7)
   private String workPackageNumber;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String workPackageName;

   @Temporal(TemporalType.DATE)
   private Date issueDate;

   @Temporal(TemporalType.DATE)
   private Date completeDate;

   @Temporal(TemporalType.DATE)
   private Date startDate;

   @Column
   private String description;

   @Column
   private String progressStatus;

   @Temporal(TemporalType.DATE)
   private Date endDate;

   @Column
   @Min(0)
   private int estimateToCompletion;

   @Column
   @Min(0)
   private int estimateAtStart;

   @Column
   private int projectNumber;

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

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
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

   public WorkPackage(String workPackageNumber, String workPackageName,
         Date issueDate, String progressStatus, Date endDate,
         int projectNumber, int estimateAtStart)
   {
      super();
      this.workPackageNumber = workPackageNumber;
      this.workPackageName = workPackageName;
      this.issueDate = issueDate;
      this.progressStatus = progressStatus;
      this.endDate = endDate;
      this.projectNumber = projectNumber;
      this.estimateAtStart = estimateAtStart;
   }

   public WorkPackage()
   {
   }

   public Date getEndDate()
   {
      return this.endDate;
   }

   public void setEndDate(final Date endDate)
   {
      this.endDate = endDate;
   }

   public int getEstimateToCompletion()
   {
      return this.estimateToCompletion;
   }

   public void setEstimateToCompletion(final int estimateToCompletion)
   {
      this.estimateToCompletion = estimateToCompletion;
   }

   public int getEstimateAtStart()
   {
      return this.estimateAtStart;
   }

   public void setEstimateAtStart(final int estimateAtStart)
   {
      this.estimateAtStart = estimateAtStart;
   }

   public int getProjectNumber()
   {
      return this.projectNumber;
   }

   public void setProjectNumber(final int projectNumber)
   {
      this.projectNumber = projectNumber;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (workPackageNumber != null && !workPackageNumber.trim().isEmpty())
         result += "workPackageNumber: " + workPackageNumber;
      if (workPackageName != null && !workPackageName.trim().isEmpty())
         result += ", workPackageName: " + workPackageName;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (progressStatus != null && !progressStatus.trim().isEmpty())
         result += ", progressStatus: " + progressStatus;
      result += ", estimateToCompletion: " + estimateToCompletion;
      result += ", estimateAtStart: " + estimateAtStart;
      result += ", projectNumber: " + projectNumber;
      return result;
   }
}
