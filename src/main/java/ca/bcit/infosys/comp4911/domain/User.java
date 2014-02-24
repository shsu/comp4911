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

import ca.bcit.infosys.comp4911.domain.Effort;

@Entity
public class User implements Serializable {

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

    @Column
    private String email;

    @Temporal(TemporalType.DATE)
    private Date startDate;

   @Column
   private boolean isHR;

   @Column
   private String status;

   // Just wondering if this should be a 1 to 1 maybe?
   @ManyToOne
   private Timesheet defaultTimesheet;

   @ManyToOne
   private PayLevel payLevel;

   @ManyToOne
   private User supervisor;

   @OneToMany(mappedBy = "supervisor")
   private Set<User> peon = new HashSet<User>();

   @ManyToOne
   private User timesheetApprover;

   @OneToMany(mappedBy = "timesheetApprover")
   private Set<User> peonsToApprove = new HashSet<User>();

   @Column
   private int paidHoursPerWeek;

   @Column
   private int totalFlexTime;

   @Column
   private int totalOvertime;

   @Column
   private int vacationDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public boolean isHR() {
        return isHR;
    }

    public void setHR(boolean isHR) {
        this.isHR = isHR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timesheet getDefaultTimesheet() {
        return defaultTimesheet;
    }

    public void setDefaultTimesheet(Timesheet defaultTimesheet) {
        this.defaultTimesheet = defaultTimesheet;
    }

    public PayLevel getPayLevel() {
        return payLevel;
    }

    public void setPayLevel(PayLevel payLevel) {
        this.payLevel = payLevel;
    }

    public User getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(User supervisor) {
        this.supervisor = supervisor;
    }

    public Set<User> getPeon() {
        return peon;
    }

    public void setPeon(Set<User> peon) {
        this.peon = peon;
    }

    public User getTimesheetApprover() {
        return timesheetApprover;
    }

    public void setTimesheetApprover(User timesheetApprover) {
        this.timesheetApprover = timesheetApprover;
    }

    public Set<User> getPeonsToApprove() {
        return peonsToApprove;
    }

    public void setPeonsToApprove(Set<User> peonsToApprove) {
        this.peonsToApprove = peonsToApprove;
    }

    public int getPaidHoursPerWeek() {
        return paidHoursPerWeek;
    }

    public void setPaidHoursPerWeek(int paidHoursPerWeek) {
        this.paidHoursPerWeek = paidHoursPerWeek;
    }

    public int getTotalFlexTime() {
        return totalFlexTime;
    }

    public void setTotalFlexTime(int totalFlexTime) {
        this.totalFlexTime = totalFlexTime;
    }

    public int getTotalOvertime() {
        return totalOvertime;
    }

    public void setTotalOvertime(int totalOvertime) {
        this.totalOvertime = totalOvertime;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public User(String username, String password, String firstName, String lastName, String email, Date startDate,
                boolean isHR, String status, Timesheet defaultTimesheet, PayLevel payLevel, User supervisor,
                Set<User> peon, User timesheetApprover, Set<User> peonsToApprove, int paidHoursPerWeek,
                int totalFlexTime, int totalOvertime, int vacationDays) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.startDate = startDate;
        this.isHR = isHR;
        this.status = status;
        this.defaultTimesheet = defaultTimesheet;
        this.payLevel = payLevel;
        this.supervisor = supervisor;
        this.peon = peon;
        this.timesheetApprover = timesheetApprover;
        this.peonsToApprove = peonsToApprove;
        this.paidHoursPerWeek = paidHoursPerWeek;
        this.totalFlexTime = totalFlexTime;
        this.totalOvertime = totalOvertime;
        this.vacationDays = vacationDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (version != user.version) return false;
        if (!id.equals(user.id)) return false;
        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + version;
        result = 31 * result + username.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", version=" + version +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", startDate=" + startDate +
                ", isHR=" + isHR +
                ", status='" + status + '\'' +
                ", defaultTimesheet=" + defaultTimesheet +
                ", payLevel=" + payLevel +
                ", supervisor=" + supervisor +
                ", peon=" + peon +
                ", timesheetApprover=" + timesheetApprover +
                ", peonsToApprove=" + peonsToApprove +
                ", paidHoursPerWeek=" + paidHoursPerWeek +
                ", totalFlexTime=" + totalFlexTime +
                ", totalOvertime=" + totalOvertime +
                ", vacationDays=" + vacationDays +
                '}';
    }
}