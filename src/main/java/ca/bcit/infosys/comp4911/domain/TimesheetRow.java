package ca.bcit.infosys.comp4911.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.lang.Override;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.WorkPackage;

@Entity
public class TimesheetRow implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Integer id = null;

   @ManyToOne
   private Project project;

   @ManyToOne
   private WorkPackage workPackage;

   public Timesheet getTimesheet()
   {
      return timesheet;
   }

   public void setTimesheet(Timesheet timesheet)
   {
      this.timesheet = timesheet;
   }

   public double getMonday()
   {
      return monday;
   }

   public void setMonday(double monday)
   {
      this.monday = monday;
   }

   public double getTuesday()
   {
      return tuesday;
   }

   public void setTuesday(double tuesday)
   {
      this.tuesday = tuesday;
   }

   public double getWednesday()
   {
      return wednesday;
   }

   public void setWednesday(double wednesday)
   {
      this.wednesday = wednesday;
   }

   public double getThursday()
   {
      return thursday;
   }

   public void setThursday(double thursday)
   {
      this.thursday = thursday;
   }

   public double getFriday()
   {
      return friday;
   }

   public void setFriday(double friday)
   {
      this.friday = friday;
   }

   public double getSaturday()
   {
      return saturday;
   }

   public void setSaturday(double saturday)
   {
      this.saturday = saturday;
   }

   public double getSunday()
   {
      return sunday;
   }

   public void setSunday(double sunday)
   {
      this.sunday = sunday;
   }

   public String getNote()
   {
      return note;
   }

   public void setNote(String note)
   {
      this.note = note;
   }

   @Version
   @Column(name = "version")
   private int version = 0;

   @JsonIgnore
   @ManyToOne(cascade = CascadeType.ALL)
   private Timesheet timesheet;

   @Column
   private double monday;

   @Column
   private double tuesday;

   @Column
   private double wednesday;

   @Column
   private double thursday;

   @Column
   private double friday;

   @Column
   private double saturday;

   @Column
   private double sunday;

   @Column
   private String note;

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
         return id.equals(((TimesheetRow) that).id);
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

   public TimesheetRow(Project project, WorkPackage workPackage, double monday, double tuesday, double wednesday, double thursday, double friday, double saturday, double sunday, String note)
   {
      this.project = project;
       this.workPackage = workPackage;
      this.monday = monday;
      this.tuesday = tuesday;
      this.wednesday = wednesday;
      this.thursday = thursday;
      this.friday = friday;
      this.saturday = saturday;
      this.sunday = sunday;
      this.note = note;
   }

   public TimesheetRow()
   {
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      result += ", monday: " + monday;
      result += ", tuesday: " + tuesday;
      result += ", wednesday: " + wednesday;
      result += ", thursday: " + thursday;
      result += ", friday: " + friday;
      result += ", saturday: " + saturday;
      result += ", sunday: " + sunday;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      return result;
   }

   public Project getProject()
   {
      return this.project;
   }

   public void setProject(final Project project)
   {
      this.project = project;
   }

   public WorkPackage getWorkPackage()
   {
      return this.workPackage;
   }

   public void setWorkPackage(final WorkPackage workPackage)
   {
      this.workPackage = workPackage;
   }
}