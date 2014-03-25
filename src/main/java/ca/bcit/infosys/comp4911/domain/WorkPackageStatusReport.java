package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.lang.Override;

@Entity
public class WorkPackageStatusReport implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   @Min(value=0,message="ID can not be smaller than 0.") 
   private Integer id = null;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Min(value=0,message="WeekNumber can not be smaller than 0.")
   private int weekNumber;

   @Column
   @Min(value=0,message="Year can not be less than 0.")
   private int year;

   @Temporal(TemporalType.DATE)
   private Date reportDate;

   @Column
   @NotNull(message="Comment can not be null.")
   @Size(max=250,message="Comment can not contian more than 250 characters.")
   private String comment;

   @Column
   @NotNull(message="WorkAccomplished can not be null.")
   @Size(max=250,message="WorkAccomplished can not contian more than 250 characters.")
   private String workAccomplished;

   @Column
   @NotNull(message="ProblemEncountered can not be null.")
   @Size(max=250,message="ProblemEncountered can not contain more than 250 characters.")
   private String problemEncountered;

   @Column
   private String workPlanned;

   @OneToMany
   private Set<Effort> estimatedWorkRemainingInPD = new HashSet<Effort>();

   @Column
   @NotNull(message="ProblemAnticipated can not be null.")
   @Size(max=250,message="ProblemAnticipated can not contain more than 250 characters.")
   private String problemAnticipated;

   @Column
   @NotBlank(message="WorkPackageNumber can not be null.")
   @Size(max=250,message="WorkPackageNumber can not contain more than 250 characters.")
   private String workPackageNumber;

   public WorkPackageStatusReport(int weekNumber, int year,
		Date reportDate, String comment, String workAccomplished,
		String problemEncountered, String workPlanned,
		Set<Effort> estimatedWorkRemainingInPD, String problemAnticipated,
		String workPackageNumber) {
	super();
	this.weekNumber = weekNumber;
	this.year = year;
	this.reportDate = reportDate;
	this.comment = comment;
	this.workAccomplished = workAccomplished;
	this.problemEncountered = problemEncountered;
	this.workPlanned = workPlanned;
	this.estimatedWorkRemainingInPD = estimatedWorkRemainingInPD;
	this.problemAnticipated = problemAnticipated;
	this.workPackageNumber = workPackageNumber;
   }

   public WorkPackageStatusReport(){
	   
   }
   public Date getReportDate()
   {
      return reportDate;
   }

   public void setReportDate(Date reportDate)
   {
      this.reportDate = reportDate;
   }

   public String getComment()
   {
      return comment;
   }

   public void setComment(String comment)
   {
      this.comment = comment;
   }

   public String getWorkAccomplished()
   {
      return workAccomplished;
   }

   public void setWorkAccomplished(String workAccomplished)
   {
      this.workAccomplished = workAccomplished;
   }

   public String getProblemEncountered()
   {
      return problemEncountered;
   }

   public void setProblemEncountered(String problemEncountered)
   {
      this.problemEncountered = problemEncountered;
   }

   public String getWorkPlanned()
   {
      return workPlanned;
   }

   public void setWorkPlanned(String workPlanned)
   {
      this.workPlanned = workPlanned;
   }

   public String getProblemAnticipated()
   {
      return problemAnticipated;
   }

   public void setProblemAnticipated(String problemAnticipated)
   {
      this.problemAnticipated = problemAnticipated;
   }

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

   public int getWeekNumber()
   {
      return this.weekNumber;
   }

   public void setWeekNumber(final int weekNumber)
   {
      this.weekNumber = weekNumber;
   }

   public int getYear()
   {
      return this.year;
   }

   public void setYear(final int year)
   {
      this.year = year;
   }

   public Set<Effort> getEstimatedWorkRemainingInPD()
   {
      return this.estimatedWorkRemainingInPD;
   }

   public void setEstimatedWorkRemainingInPD(
         final Set<Effort> estimatedWorkRemainingInPD)
   {
      this.estimatedWorkRemainingInPD = estimatedWorkRemainingInPD;
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
         return id.equals(((WorkPackageStatusReport) that).id);
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

   public String getWorkPackageNumber()
   {
      return this.workPackageNumber;
   }

   public void setWorkPackageNumber(final String workPackageNumber)
   {
      this.workPackageNumber = workPackageNumber;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "weekNumber: " + weekNumber;
      result += ", year: " + year;
      if (comment != null && !comment.trim().isEmpty())
         result += ", comment: " + comment;
      if (workAccomplished != null && !workAccomplished.trim().isEmpty())
         result += ", workAccomplished: " + workAccomplished;
      if (problemEncountered != null && !problemEncountered.trim().isEmpty())
         result += ", problemEncountered: " + problemEncountered;
      if (workPlanned != null && !workPlanned.trim().isEmpty())
         result += ", workPlanned: " + workPlanned;
      if (problemAnticipated != null && !problemAnticipated.trim().isEmpty())
         result += ", problemAnticipated: " + problemAnticipated;
      if (workPackageNumber != null && !workPackageNumber.trim().isEmpty())
         result += ", workPackageNumber: " + workPackageNumber;
      return result;
   }

}