package ca.bcit.infosys.comp4911.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.lang.Override;
import ca.bcit.infosys.comp4911.domain.PayLevel;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Integer id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String username;

   @Column
   private String password;

   @Column
   private String firstName;

   @Column
   private String lastName;

   @Temporal(TemporalType.DATE)
   private Date startDate;

   @Column
   private boolean isHR;

   @Column
   private String status;

   // Just wondering if this should be a 1 to 1 maybe?
   @ManyToOne
   private Timesheet defaulTimesheet;

   @ManyToOne
   private PayLevel payLevel;

   @ManyToOne
   private User supervisor;

   @OneToMany(mappedBy="supervisor")
   private Set<User> peon = new HashSet<User>();

   @ManyToOne
   private User timesheetApprover;

   @OneToMany(mappedBy="timesheetApprover")
   private Set<User> peonsToApprove = new HashSet<User>();

   public boolean isHR()
   {
      return isHR;
   }

   public void setHR(boolean isHR)
   {
      this.isHR = isHR;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus(String status)
   {
      this.status = status;
   }

   public Timesheet getDefaulTimesheet()
   {
      return defaulTimesheet;
   }

   public void setDefaulTimesheet(Timesheet defaulTimesheet)
   {
      this.defaulTimesheet = defaulTimesheet;
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
         return id.equals(((User) that).id);
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

   public String getUsername()
   {
      return this.username;
   }

   public void setUsername(final String username)
   {
      this.username = username;
   }

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public String getFirstName()
   {
      return this.firstName;
   }

   public void setFirstName(final String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return this.lastName;
   }

   public void setLastName(final String lastName)
   {
      this.lastName = lastName;
   }

   public User()
   {
   }

   public User(String username, String password, String firstName, String lastName)
   {
      this.username = username;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
   }

   public Date getStartDate()
   {
      return this.startDate;
   }

   public void setStartDate(final Date startDate)
   {
      this.startDate = startDate;
   }

   public PayLevel getPayLevel()
   {
      return this.payLevel;
   }

   public void setPayLevel(final PayLevel payLevel)
   {
      this.payLevel = payLevel;
   }

   public User getSupervisor()
   {
      return this.supervisor;
   }

   public void setSupervisor(final User supervisor)
   {
      this.supervisor = supervisor;
   }

   public Set<User> getPeon()
   {
      return this.peon;
   }

   public void setPeon(final Set<User> peon)
   {
      this.peon = peon;
   }

   public User getTimesheetApprover()
   {
      return this.timesheetApprover;
   }

   public void setTimesheetApprover(final User timesheetApprover)
   {
      this.timesheetApprover = timesheetApprover;
   }

   public Set<User> getPeonsToApprove()
   {
      return this.peonsToApprove;
   }

   public void setPeonsToApprove(final Set<User> peonsToApprove)
   {
      this.peonsToApprove = peonsToApprove;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      if (username != null && !username.trim().isEmpty())
         result += ", username: " + username;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (firstName != null && !firstName.trim().isEmpty())
         result += ", firstName: " + firstName;
      if (lastName != null && !lastName.trim().isEmpty())
         result += ", lastName: " + lastName;
      result += ", isHR: " + isHR;
      if (status != null && !status.trim().isEmpty())
         result += ", status: " + status;
      return result;
   }
}