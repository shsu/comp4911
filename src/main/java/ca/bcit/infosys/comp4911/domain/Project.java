package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.Override;

@Entity
public class Project implements Serializable
{

   @Id
   @Column(updatable = false, nullable = false)
   @Min(value=0, message="ID can not be smaller than 0.") 
   private Integer projectNumber;
   
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column(nullable = false)
   @NotBlank(message="ProjectName can not be blank.")
   @Size(max=250,message="ProjectName can not contain more than 250 characters.")
   private String projectName;

   @Temporal(TemporalType.DATE)
   @Column(nullable = false)
   private Date issueDate;

   @Temporal(TemporalType.DATE)
   private Date completeDate;

   @Column
   @Min(value=0,message="ClientRate can not be less than 0.")
   private BigDecimal clientRate;

   @Column
   @Min(value = 0,message="UnAllocatedBudget can not be less than 0.")
   private BigDecimal UnAllocatedBudget;

   @Column
   @Min(value=0,message="AllocatedBudget can not be less than 0.")
   private BigDecimal AllocatedBudget;

    public Integer getProjectNumber()
    {
        return this.projectNumber;
    }

    public void setProjectNumber(final Integer projectNumber)
    {
        this.projectNumber = projectNumber;
    }

   public int getVersion()
   {
      return version;
   }

   public void setVersion(int version)
   {
      this.version = version;
   }

   public String getProjectName()
   {
      return projectName;
   }

   public void setProjectName(String projectName)
   {
      this.projectName = projectName;
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

   public BigDecimal getClientRate()
   {
      return clientRate;
   }

   public void setClientRate(BigDecimal clientRate)
   {
      this.clientRate = clientRate;
   }

   public BigDecimal getUnAllocatedBudget()
   {
      return UnAllocatedBudget;
   }

   public void setUnAllocatedBudget(BigDecimal unAllocatedBudget)
   {
      UnAllocatedBudget = unAllocatedBudget;
   }

   public BigDecimal getAllocatedBudget()
   {
      return AllocatedBudget;
   }

   public void setAllocatedBudget(BigDecimal allocatedBudget)
   {
      AllocatedBudget = allocatedBudget;
   }

   public Project(Integer projectNumber, String projectName, Date issueDate, Date completeDate,
         BigDecimal clientRate, BigDecimal unAllocatedBudget,
         BigDecimal allocatedBudget)
   {
      this.projectNumber = projectNumber;
      this.projectName = projectName;
      this.issueDate = issueDate;
      this.completeDate = completeDate;
      this.clientRate = clientRate;
      UnAllocatedBudget = unAllocatedBudget;
      AllocatedBudget = allocatedBudget;
   }
   
   /**
    * Construct a Project with projectNumber and projectName,
    * default issueDate to CurrentDate,
    * default completeDate to null,
    * default clientRate to 0,
    * default UnAllocatedBudget to 0, and
    * default AllocatedBudget to 0.
    * @param projectNumber
    * @param projectName
    */
   public Project(Integer projectNumber, String projectName)
	   {
	      this.projectNumber = projectNumber;
	      this.projectName = projectName;
	      this.issueDate = DateTime.now().toDate();
	      this.completeDate = null;
	      this.clientRate = BigDecimal.ZERO;
	      UnAllocatedBudget = BigDecimal.ZERO;
	      AllocatedBudget = BigDecimal.ZERO;
	   }

   public Project()
   {
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      Project project = (Project) o;

      if (projectNumber != project.projectNumber)
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      return projectNumber.hashCode();
   }
}