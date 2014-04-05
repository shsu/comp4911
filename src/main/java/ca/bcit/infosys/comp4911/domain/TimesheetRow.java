package ca.bcit.infosys.comp4911.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.lang.Override;
import java.util.Date;

@Entity
@Table(name = "TIMESHEET_ROW")
public class TimesheetRow implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "ROW_ID", updatable = false, nullable = false)
   @Min(value=0,message="ID can not be smaller than 0.") 
   private Integer id = null;

   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   //@NotBlank(message="WorkPackageNumber can not be blank.")  
   @Size(min = 0, max = 7,message="Work Package Number can not contain more than 7 charaters.")
   @NotNull(message="WorkPackageNumber can not be null.")
   private String workPackageNumber;

   // All these values are now in tenths
   @Column
   @Min(value=0,message="Attribute monday can not be less than 0.")
   private int monday;

   @Column
   @Min(value=0,message="Attribute tuesday can not be less than 0.")
   private int tuesday;

   @Column
   @Min(value=0,message="Attribute wednesday can not be less than 0.")
   private int wednesday;

   @Column
   @Min(value=0,message="Attribute thursday can not be less than 0.")
   private int thursday;

   @Column
   @Min(value=0,message="Attribute friday can not be less than 0.")
   private int friday;

   @Column
   @Min(value=0,message="Attribute saturday can not be less than 0.")
   private int saturday;

   @Column
   @Min(value=0,message="Attribute sunday can not be less than 0.")
   private int sunday;

   @Column
   @Size(max=250,message="Note can not contain more than 250 characters.") 
   private String note;

   @Column
   @Min(value=0,message="ProjectNumber can not be smaller than 0.")
   private int projectNumber;

   @Column
   private PLevel pLevel;

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

   public String getWorkPackageNumber()
   {
      return workPackageNumber;
   }

   public void setWorkPackageNumber(String workPackageNumber)
   {
      this.workPackageNumber = workPackageNumber;
   }

   public int getMonday()
   {
      return monday;
   }

   public void setMonday(int monday)
   {
      this.monday = monday;
   }

   public int getTuesday()
   {
      return tuesday;
   }

   public void setTuesday(int tuesday)
   {
      this.tuesday = tuesday;
   }

   public int getWednesday()
   {
      return wednesday;
   }

   public void setWednesday(int wednesday)
   {
      this.wednesday = wednesday;
   }

   public int getThursday()
   {
      return thursday;
   }

   public void setThursday(int thursday)
   {
      this.thursday = thursday;
   }

   public int getFriday()
   {
      return friday;
   }

   public void setFriday(int friday)
   {
      this.friday = friday;
   }

   public int getSaturday()
   {
      return saturday;
   }

   public void setSaturday(int saturday)
   {
      this.saturday = saturday;
   }

   public int getSunday()
   {
      return sunday;
   }

   public void setSunday(int sunday)
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

    public PLevel getpLevel() { return pLevel; }

    public void setpLevel(PLevel pLevel) { this.pLevel = pLevel; }

    public int calculateTotal(){
        return monday + tuesday + wednesday + thursday + friday + saturday + sunday;
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

   public TimesheetRow(int projectNumber, String workPackageNumber, int monday, int tuesday, int wednesday, int thursday,
                       int friday, int saturday, int sunday, String note, PLevel pLevel)
   {
      this.projectNumber = projectNumber;
      this.workPackageNumber = workPackageNumber;
      this.monday = monday;
      this.tuesday = tuesday;
      this.wednesday = wednesday;
      this.thursday = thursday;
      this.friday = friday;
      this.saturday = saturday;
      this.sunday = sunday;
      this.note = note;
      this.pLevel = pLevel;
   }
   
   /**
    * Construct a TimesheetRow with projectnumber and workPackagenumber,
    * default monday to 0,
    * default tuesday to 0,
    * default wednesday to 0,
    * default thursday to 0,
    * default friday to 0,
    * default saturday to 0,
    * defaut suday to 0, and
    * default note to blank string.
    * @param projectNumber
    * @param workPackageNumber
    * @param pLevel
    */
   public TimesheetRow(int projectNumber, String workPackageNumber, PLevel pLevel)
   {
      this.projectNumber = projectNumber;
      this.workPackageNumber = workPackageNumber;
      this.monday = 0;
      this.tuesday = 0;
      this.wednesday = 0;
      this.thursday = 0;
      this.friday = 0;
      this.saturday = 0;
      this.sunday = 0;
      this.note = "";
      this.pLevel = pLevel;
   }

   public TimesheetRow()
   {
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
      result += ", monday: " + monday;
      result += ", tuesday: " + tuesday;
      result += ", wednesday: " + wednesday;
      result += ", thursday: " + thursday;
      result += ", friday: " + friday;
      result += ", saturday: " + saturday;
      result += ", sunday: " + sunday;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      result += ", projectNumber: " + projectNumber;
      return result;
   }

}