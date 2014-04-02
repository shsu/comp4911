package ca.bcit.infosys.comp4911.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.lang.Override;
import java.util.List;

@Entity
public class WorkPackage implements Serializable
{
   @Id
   @Column(updatable = false, nullable = false, name = "WORKPACKAGE_NUMBER")
   @Size(min = 6, max = 7,message="WorkPackageNumber size must be between 7 and 250.")
   @NotBlank(message="WorkPackageNumber can not be blank.")
   private String workPackageNumber;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @NotBlank(message="WorkPackageName can not be blank.")
   @Size(max=250,message="WorkPackageName can not contain more than 250 characters.")
   private String workPackageName;

   @Temporal(TemporalType.DATE)
   private Date issueDate;

   @Temporal(TemporalType.DATE)
   private Date completeDate;

   @Temporal(TemporalType.DATE)
   private Date startDate;

   @Column
   @NotNull(message="Description can not be null.")
   @Size(max=250,message="Description can not contain more than 250 characters.")
   private String description;

    /** This is supposed to be Active or Inactive I believe */
   @Column
   @NotNull(message="ProgressStatus can not be null.")
   @Size(max=250,message="ProgressStatus can not contain more than 250 characters.")
   private String progressStatus;

   @Temporal(TemporalType.DATE)
   private Date endDate;

   @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
   @Size(min=5, max=7)
   @JoinTable(name="WORKPACKAGE_EFFORT",
           joinColumns = { @JoinColumn(name="WORKPACKAGE_NUMBER")},
           inverseJoinColumns = { @JoinColumn(name="EFFORT_ID")})
   private List<Effort> estimateAtStart;

   @Column
   @Min(value=0,message="ProjectNumber can not be smaller than 0.")
   @NotNull(message="projectnumber can not be null")
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
         int projectNumber, List<Effort> estimateAtStart)
   {
      super();
      this.workPackageNumber = workPackageNumber;
      this.workPackageName = workPackageName;
      this.issueDate = issueDate;
      this.progressStatus = progressStatus;
      this.endDate = endDate;
      this.projectNumber = projectNumber;
      this.estimateAtStart = estimateAtStart;
      this.description = "";
      this.completeDate = issueDate;
      this.startDate = issueDate;
   }
   
   /**
    * WorkPackage constructor, taking workPackageNumber, workPackageName and projectName,
    * default issueDate to current date,
    * default progressStatus to empty string,
    * default endDate to null,
    * defaultestimateAtStart to an Empty List<Effort>,
    * default 
    * @param workPackageNumber
    * defailt estimateToCompletion to an empty List<Effort>,
    * default description to empty string,
    * default coompleteDate to issueDate, and
    * default startDate to issueDate
    * @param workPackageName
    * @param projectNumber
    */
   public WorkPackage(String workPackageNumber, String workPackageName, int projectNumber)
	   {
	      super();
	      this.workPackageNumber = workPackageNumber;
	      this.workPackageName = workPackageName;
	      this.issueDate = DateTime.now().toDate();
	      this.progressStatus = "";
	      this.endDate = null;
	      this.projectNumber = projectNumber;
	      this.estimateAtStart = new ArrayList<Effort>();
	      this.description = "";
	      this.completeDate = issueDate;
	      this.startDate = issueDate;
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

   public List<Effort> getEstimateAtStart()
   {
      return this.estimateAtStart;
   }

   public void setEstimateAtStart(final List<Effort> estimateAtStart)
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
      result += ", estimateAtStart: " + estimateAtStart;
      result += ", projectNumber: " + projectNumber;
      return result;
   }
}
