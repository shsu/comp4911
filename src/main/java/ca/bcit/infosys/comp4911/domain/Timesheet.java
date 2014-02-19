package ca.bcit.infosys.comp4911.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.lang.Override;
import java.util.ArrayList;
import java.util.List;
import ca.bcit.infosys.comp4911.domain.User;

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

   @OneToMany(mappedBy = "timesheet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<TimesheetRow> timesheetRows;

   @Column
   private int weekNumber;

   @Column
   private int year;

   @Column
   private double flexTime;

   @Column
   private double overTime;

   @Column
   private boolean isApproved;

   @Column
   private boolean isSigned;

   @ManyToOne
   private User user;

   public List<TimesheetRow> getTimesheetRows()
   {
      // Hibernate.initialize(timesheetRows);
      return timesheetRows;
   }

   public void setTimesheetRows(List<TimesheetRow> timesheetRows)
   {
      this.timesheetRows = timesheetRows;
   }

   public double getFlexTime()
   {
      return flexTime;
   }

   public void setFlexTime(double flexTime)
   {
      this.flexTime = flexTime;
   }

   public double getOverTime()
   {
      return overTime;
   }

   public void setOverTime(double overTime)
   {
      this.overTime = overTime;
   }

   public boolean isApproved()
   {
      return isApproved;
   }

   public void setApproved(boolean isApproved)
   {
      this.isApproved = isApproved;
   }

   public boolean isSigned()
   {
      return isSigned;
   }

   public void setSigned(boolean isSigned)
   {
      this.isSigned = isSigned;
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

   public boolean getIsApproved()
   {
      return this.isApproved;
   }

   public void setIsApproved(final boolean isApproved)
   {
      this.isApproved = isApproved;
   }

   public Timesheet(User user, int weekNumber, int year, double flexTime, double overTime, boolean isApproved, boolean isSigned)
   {
      this.user = user;
      this.weekNumber = weekNumber;
      this.year = year;
      this.flexTime = flexTime;
      this.overTime = overTime;
      this.isApproved = isApproved;
      this.isSigned = isSigned;
      timesheetRows = new ArrayList<TimesheetRow>();
   }

   public Timesheet()
   {
      timesheetRows = new ArrayList<TimesheetRow>();
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      result += ", weekNumber: " + weekNumber;
      result += ", year: " + year;
      result += ", flexTime: " + flexTime;
      result += ", overTime: " + overTime;
      result += ", isApproved: " + isApproved;
      result += ", isSigned: " + isSigned;
      return result;
   }

   public User getUser()
   {
      return this.user;
   }

   public void setUser(final User user)
   {
      this.user = user;
   }

}