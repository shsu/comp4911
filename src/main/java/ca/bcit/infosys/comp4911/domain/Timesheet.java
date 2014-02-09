package ca.bcit.infosys.comp4911.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Timesheet implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Integer id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private int weekNumber;

   @Column
   private int year;

   @Column
   private int monday;

   @Column
   private int tuesday;

   @Column
   private int wednesday;

   @Column
   private int thursday;

   @Column
   private int friday;

   @Column
   private int saturday;

   @Column
   private int sunday;

   @Column
   private int note;

   @Column
   private int isApproved;

   @ManyToOne
   private User userID;

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
         return id.equals(((Timesheet) that).id);
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

   public int getMonday()
   {
      return this.monday;
   }

   public void setMonday(final int monday)
   {
      this.monday = monday;
   }

   public int getTuesday()
   {
      return this.tuesday;
   }

   public void setTuesday(final int tuesday)
   {
      this.tuesday = tuesday;
   }

   public int getWednesday()
   {
      return this.wednesday;
   }

   public void setWednesday(final int wednesday)
   {
      this.wednesday = wednesday;
   }

   public int getThursday()
   {
      return this.thursday;
   }

   public void setThursday(final int thursday)
   {
      this.thursday = thursday;
   }

   public int getFriday()
   {
      return this.friday;
   }

   public void setFriday(final int friday)
   {
      this.friday = friday;
   }

   public int getSaturday()
   {
      return this.saturday;
   }

   public void setSaturday(final int saturday)
   {
      this.saturday = saturday;
   }

   public int getSunday()
   {
      return this.sunday;
   }

   public void setSunday(final int sunday)
   {
      this.sunday = sunday;
   }

   public int getNote()
   {
      return this.note;
   }

   public void setNote(final int note)
   {
      this.note = note;
   }

   public int getIsApproved()
   {
      return this.isApproved;
   }

   public void setIsApproved(final int isApproved)
   {
      this.isApproved = isApproved;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "weekNumber: " + weekNumber;
      result += ", year: " + year;
      result += ", monday: " + monday;
      result += ", tuesday: " + tuesday;
      result += ", wednesday: " + wednesday;
      result += ", thursday: " + thursday;
      result += ", friday: " + friday;
      result += ", saturday: " + saturday;
      result += ", sunday: " + sunday;
      result += ", note: " + note;
      result += ", isApproved: " + isApproved;
      return result;
   }

   public User getUserID()
   {
      return this.userID;
   }

   public void setUserID(final User UserID)
   {
      this.userID = UserID;
   }
}