package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;

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
   private int userID;

   @Column
   private int weekNumber;

   @Column
   private int year;

   @Column
   private boolean isApproved;

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

   public int getUserID()
   {
      return this.userID;
   }

   public void setUserID(final int userID)
   {
      this.userID = userID;
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

   public boolean getIsApproved()
   {
      return this.isApproved;
   }

   public void setIsApproved(final boolean isApproved)
   {
      this.isApproved = isApproved;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "userID: " + userID;
      result += ", weekNumber: " + weekNumber;
      result += ", year: " + year;
      result += ", isApproved: " + isApproved;
      return result;
   }
}