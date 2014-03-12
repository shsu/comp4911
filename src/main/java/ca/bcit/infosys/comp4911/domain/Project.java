package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.Override;

@Entity
public class Project implements Serializable
{

   @Id
   @Column(updatable = false, nullable = false)
   private Integer projectNumber;
   
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column(nullable = false)
   private String projectName;

   @Temporal(TemporalType.DATE)
   @Column(nullable = false)
   private Date issueDate;

   @Temporal(TemporalType.DATE)
   private Date completeDate;

   @Column
   private BigDecimal clientRate;

   @Column
   private BigDecimal UnAllocatedBudget;

   @Column
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