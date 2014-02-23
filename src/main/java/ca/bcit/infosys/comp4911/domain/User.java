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

}