package ca.bcit.infosys.comp4911.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
public class User implements Serializable
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
   @NotBlank(message="UserName can not be blank.")
   @Size(max=250,message="UserName can not contain more than 250 characteres.")
   private String username;

   @Column
   @NotBlank(message="Password can not be blank.")
   @Size(max=250,message="Password can not contains more than 250 characters.")
   private String password;

   @Column
   @NotBlank(message="FirstName can not be blank.")
   @Size(max=250,message="FirstName can not contain more than 250 characters.")
   private String firstName;

   @Column
   @NotBlank(message="LastName can not be blank.")
   @Size(max=250,message="LastName can not contain more than 250 characters.")
   private String lastName;

   @Temporal(TemporalType.DATE)
   private Date startDate;

   @Column
   private boolean hr;

   @Column
   @NotNull(message="Status can not be null.")
   @Size(max=250,message="Status can not contain more than 250 characters.")
   private String status;

   @Column
   @Min(value=0,message="PaidHourPerWeek can not be less than 0.")
   private int paidHoursPerWeek;

   @Column
   @Min(value=0,message="YotalFlexTime can not be less than 0.")
   private int totalFlexTime;

   @Column
   @Min(value=0,message="VacationDays can not be less than 0.")
   private int vacationDays;

   @Column
   @Min(value=0,message="DefaultTimesheetID can not be smaller than 0.")
   private int defaultTimesheetID;

   @Column
   @Min(value=0,message="SupervisorUserID can not be smaller than 0.")
   private int supervisorUserID;

   @Column
   @Min(value=0,message="TimesheetApproverUserID can not be smaller than 0.")
   private int timesheetApproverUserID;

   @Column
   private PLevel pLevel;

   public Integer getId()
   {
      return id;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return version;
   }

   public void setVersion(int version)
   {
      this.version = version;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername(String username)
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   public Date getStartDate()
   {
      return startDate;
   }

   public void setStartDate(Date startDate)
   {
      this.startDate = startDate;
   }

   public boolean isHR()
   {
      return hr;
   }

   public void setHR(boolean hr)
   {
      this.hr = hr;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus(String status)
   {
      this.status = status;
   }

   public int getPaidHoursPerWeek()
   {
      return paidHoursPerWeek;
   }

   public void setPaidHoursPerWeek(int paidHoursPerWeek)
   {
      this.paidHoursPerWeek = paidHoursPerWeek;
   }

   public int getTotalFlexTime()
   {
      return totalFlexTime;
   }

   public void setTotalFlexTime(int totalFlexTime)
   {
      this.totalFlexTime = totalFlexTime;
   }

   public int getVacationDays()
   {
      return vacationDays;
   }

   public void setVacationDays(int vacationDays)
   {
      this.vacationDays = vacationDays;
   }

   public User()
   {
   }

    public User(final String username, final String password, final String firstName, final String lastName,
                final Date startDate, final boolean hr, final String status, final int paidHoursPerWeek,
                final int totalFlexTime, final int vacationDays, final int defaultTimesheetID,
                final int supervisorUserID, final int timesheetApproverUserID, final PLevel pLevel) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
        this.hr = hr;
        this.status = status;
        this.paidHoursPerWeek = paidHoursPerWeek;
        this.totalFlexTime = totalFlexTime;
        this.vacationDays = vacationDays;
        this.defaultTimesheetID = defaultTimesheetID;
        this.supervisorUserID = supervisorUserID;
        this.timesheetApproverUserID = timesheetApproverUserID;
        this.pLevel = pLevel;
    }
    
    /**
     * Constructor of User, take userName, passwordm firstName, lastName and supervisorID,
     * default startDate to current date,
     * default isHR o false,
     * default status to Active,
     * default paidHoursPerWeek to 0,
     * default totalFlexTime to 0,
     * default vacationDays to 0,
     * default defaultTimesheetID to 0,
     * default timesheetApproverUserID to supervisorUserID, and
     * default pLevel to P1
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param supervisorUserID
     */
    public User(final String username, final String password, final String firstName, final String lastName,
            final int supervisorUserID) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.startDate = DateTime.now().toDate();
    this.hr = false;
    this.status = "Active";
    this.paidHoursPerWeek = 40;
    this.totalFlexTime = 0;
    this.vacationDays = 0;
    this.defaultTimesheetID = 0;
    this.supervisorUserID = supervisorUserID;
    this.timesheetApproverUserID = supervisorUserID;
    this.pLevel = PLevel.P1;
}

    @Override
   public boolean equals(Object o)
   {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;

      User user = (User) o;

      if (version != user.version)
         return false;
      if (!id.equals(user.id))
         return false;
      if (!username.equals(user.username))
         return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      int result = id.hashCode();
      result = 31 * result + version;
      result = 31 * result + username.hashCode();
      return result;
   }

   public int getDefaultTimesheetID()
   {
      return this.defaultTimesheetID;
   }

   public void setDefaultTimesheetID(final int defaultTimesheetID)
   {
      this.defaultTimesheetID = defaultTimesheetID;
   }

   public int getSupervisorUserID()
   {
      return this.supervisorUserID;
   }

   public void setSupervisorUserID(final int supervisorUserID)
   {
      this.supervisorUserID = supervisorUserID;
   }

   public int getTimesheetApproverUserID()
   {
      return this.timesheetApproverUserID;
   }

   public void setTimesheetApproverUserID(final int timesheetApproverUserID)
   {
      this.timesheetApproverUserID = timesheetApproverUserID;
   }

    public PLevel getpLevel() {
        return pLevel;
    }

    public void setpLevel(PLevel pLevel) {
        this.pLevel = pLevel;
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
      result += ", hr: " + hr;
      if (status != null && !status.trim().isEmpty())
         result += ", status: " + status;
      result += ", paidHoursPerWeek: " + paidHoursPerWeek;
      result += ", totalFlexTime: " + totalFlexTime;
      result += ", vacationDays: " + vacationDays;
      result += ", defaultTimesheetID: " + defaultTimesheetID;
      result += ", supervisorUserID: " + supervisorUserID;
      result += ", timesheetApproverUserID: " + timesheetApproverUserID;
      result += ", pLevel: " + pLevel;
      return result;
   }
}