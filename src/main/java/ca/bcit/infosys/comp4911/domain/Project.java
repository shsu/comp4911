package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.lang.Override;
import java.util.Currency;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import ca.bcit.infosys.comp4911.domain.Effort;

@Entity
public class Project implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Integer id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String projectName;

   @Temporal(TemporalType.DATE)
   private Date issueDate;

   @Temporal(TemporalType.DATE)
   private Date completeDate;

   @Column
   private Currency ActualCostWorkPerformedInDollars;

   @Column
   private int ActualCostWorkPerformedInPD;

   @Column
   private Currency BudgetedCostWorkPerformedInDollars;

   @Column
   private int string;

   @Column
   private int BudgetedCostWorkPerformedInPD;

   @Column
   private Currency BudgetedCostWorkScheduledInDollars;

   @Column
   private Currency estimateAtCompletionInDollars;

   @Column
   private int estimateAtCompletionInPD;

   @Column
   private Currency estimateToCompletionInDollars;

   @Column
   private int estimateToCompletionInPD;

   @OneToMany(mappedBy = "project")
   private Set<WorkPackage> workPackages = new HashSet<WorkPackage>();

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
         return id.equals(((Project) that).id);
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

   public String getProjectName()
   {
      return this.projectName;
   }

   public void setProjectName(final String projectName)
   {
      this.projectName = projectName;
   }

   public Date getIssueDate()
   {
      return this.issueDate;
   }

   public void setIssueDate(final Date issueDate)
   {
      this.issueDate = issueDate;
   }

   public Date getCompleteDate()
   {
      return this.completeDate;
   }

   public void setCompleteDate(final Date completeDate)
   {
      this.completeDate = completeDate;
   }

   public Currency getActualCostWorkPerformedInDollars()
   {
      return this.ActualCostWorkPerformedInDollars;
   }

   public void setActualCostWorkPerformedInDollars(
         final Currency ActualCostWorkPerformedInDollars)
   {
      this.ActualCostWorkPerformedInDollars = ActualCostWorkPerformedInDollars;
   }

   public int getActualCostWorkPerformedInPD()
   {
      return this.ActualCostWorkPerformedInPD;
   }

   public void setActualCostWorkPerformedInPD(final int ActualCostWorkPerformedInPD)
   {
      this.ActualCostWorkPerformedInPD = ActualCostWorkPerformedInPD;
   }

   public Currency getBudgetedCostWorkPerformedInDollars()
   {
      return this.BudgetedCostWorkPerformedInDollars;
   }

   public void setBudgetedCostWorkPerformedInDollars(
         final Currency BudgetedCostWorkPerformedInDollars)
   {
      this.BudgetedCostWorkPerformedInDollars = BudgetedCostWorkPerformedInDollars;
   }

   public int getString()
   {
      return this.string;
   }

   public void setString(final int string)
   {
      this.string = string;
   }

   public int getBudgetedCostWorkPerformedInPD()
   {
      return this.BudgetedCostWorkPerformedInPD;
   }

   public void setBudgetedCostWorkPerformedInPD(
         final int BudgetedCostWorkPerformedInPD)
   {
      this.BudgetedCostWorkPerformedInPD = BudgetedCostWorkPerformedInPD;
   }

   public Currency getBudgetedCostWorkScheduledInDollars()
   {
      return this.BudgetedCostWorkScheduledInDollars;
   }

   public void setBudgetedCostWorkScheduledInDollars(
         final Currency BudgetedCostWorkScheduledInDollars)
   {
      this.BudgetedCostWorkScheduledInDollars = BudgetedCostWorkScheduledInDollars;
   }

   public Currency getEstimateAtCompletionInDollars()
   {
      return this.estimateAtCompletionInDollars;
   }

   public void setEstimateAtCompletionInDollars(
         final Currency estimateAtCompletionInDollars)
   {
      this.estimateAtCompletionInDollars = estimateAtCompletionInDollars;
   }

   public int getEstimateAtCompletionInPD()
   {
      return this.estimateAtCompletionInPD;
   }

   public void setEstimateAtCompletionInPD(final int estimateAtCompletionInPD)
   {
      this.estimateAtCompletionInPD = estimateAtCompletionInPD;
   }

   public Currency getEstimateToCompletionInDollars()
   {
      return this.estimateToCompletionInDollars;
   }

   public void setEstimateToCompletionInDollars(
         final Currency estimateToCompletionInDollars)
   {
      this.estimateToCompletionInDollars = estimateToCompletionInDollars;
   }

   public int getEstimateToCompletionInPD()
   {
      return this.estimateToCompletionInPD;
   }

   public void setEstimateToCompletionInPD(final int estimateToCompletionInPD)
   {
      this.estimateToCompletionInPD = estimateToCompletionInPD;
   }

   public Set<WorkPackage> getWorkPackages()
   {
      return this.workPackages;
   }

   public void setWorkPackages(final Set<WorkPackage> workPackages)
   {
      this.workPackages = workPackages;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      if (projectName != null && !projectName.trim().isEmpty())
         result += ", projectName: " + projectName;
      result += ", ActualCostWorkPerformedInPD: " + ActualCostWorkPerformedInPD;
      result += ", string: " + string;
      result += ", BudgetedCostWorkPerformedInPD: "
            + BudgetedCostWorkPerformedInPD;
      result += ", estimateAtCompletionInPD: " + estimateAtCompletionInPD;
      result += ", estimateToCompletionInPD: " + estimateToCompletionInPD;
      return result;
   }
}