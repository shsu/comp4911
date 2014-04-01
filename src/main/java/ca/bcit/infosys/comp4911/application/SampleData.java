package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.domain.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.math.BigDecimal;
import java.util.*;

@Singleton
@Startup
public class SampleData {

    @EJB
    private UserDao userDao;

    @EJB
    private TimesheetDao timesheetDao;

    @EJB
    private TimesheetRowDao timesheetRowDao;

    @EJB
    private PayRateDao payRateDao;

    @EJB
    private ProjectDao projectDao;

    @EJB
    private ProjectAssignmentDao projectAssignmentDao;

    @EJB
    private WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    private WorkPackageStatusReportDao workPackageStatusReportDao;

    @EJB
    private WorkPackageDao workPackageDao;

    @EJB
    private EffortDao effortDao;


    public SampleData() {
    }

    @PostConstruct
    public void populateData() {
        generateUsers();
        generateProjects();
        generatePayRates();
        generateProjectAssignments();
        generateEffort();
        generateWorkPackages();
        generateWorkPackageAssignments();
        generateTimesheets();
        //generateWorkPackageStatusReports();
    }

    private void generateUsers() {
        // Keep the default timesheet id as -1
        userDao.create(new User(
                "q", "q", "FirstName", "LastName", new Date(), true, "Active", 40, 50, 0, 0, 0, 0, PLevel.P5),false);

        userDao.create(new User(
          "username0@example.com", "password", "Bruce", "Link", new Date(), true, "Active", 40, 0,
          0, 0, 1, 1, PLevel.P5),false);

        Date startDate = setDate(1, 1, 2000);
        
        userDao.create(new User(
        		"jedward@example.com", "q", "John", "Edward", startDate, false, "Active", 40, 0, 14,
        		12345678, 1, 1, PLevel.P1),false);
        
        userDao.create(new User(
                "awong@example.com", "q", "Alice", "Wong", startDate, false, "Active", 40, 0, 14,
                23456789, 1, 1, PLevel.P2),false);
        
        userDao.create(new User(
                "bnelson@example.com", "q", "Bob", "Nelson", startDate, false, "Active", 40, 0, 14,
                34567890, 56789012, 56789012, PLevel.P3),false);
        
        userDao.create(new User(
                "csandhu@example.com", "q", "Christine", "Sandhu", startDate, true, "Active", 40, 0, 14,
                45678901, 56789012, 56789012, PLevel.P4),false);
        
        userDao.create(new User(
                "zcantwell@example.com", "q", "Zamir", "Cantwell", startDate, false, "Active", 40, 0, 14,
                56789012, 0, 0, PLevel.P5),false);
        
        userDao.create(new User(
                "cpark@example.com", "q", "Chi-En", "Park", startDate, false, "Active", 40, 0, 14,
                67890123, 56789012, 56789012, PLevel.SS),false);
        
        userDao.create(new User(
                "gsmith@example.com", "q", "Grace", "Smith", startDate, false, "Active", 40, 0, 14,
                78901234, 56789012, 56789012, PLevel.DS),false);
    }

    private void generatePayRates() {
        payRateDao.create(new PayRate(
            2017, new BigDecimal(80), PLevel.P1),false);
        
        payRateDao.create(new PayRate(
            2017, new BigDecimal(100), PLevel.P2),false);
        
        payRateDao.create(new PayRate(
            2017, new BigDecimal(125), PLevel.P3),false);
        
        payRateDao.create(new PayRate(
            2017, new BigDecimal(175), PLevel.P4),false);
        
        payRateDao.create(new PayRate(
            2017, new BigDecimal(215), PLevel.P5),false);
        
        payRateDao.create(new PayRate(
            2017, new BigDecimal(115), PLevel.DS),false);
        
        payRateDao.create(new PayRate(
            2017, new BigDecimal(115), PLevel.SS),false);
        
        
        payRateDao.create(new PayRate(
            2016, new BigDecimal(76), PLevel.P1),false);
            
        payRateDao.create(new PayRate(
            2016, new BigDecimal(96), PLevel.P2),false);
            
        payRateDao.create(new PayRate(
            2016, new BigDecimal(120), PLevel.P3),false);
            
        payRateDao.create(new PayRate(
            2016, new BigDecimal(170), PLevel.P4),false);
            
        payRateDao.create(new PayRate(
            2016, new BigDecimal(210), PLevel.P5),false);
            
        payRateDao.create(new PayRate(
            2016, new BigDecimal(110), PLevel.DS),false);
            
        payRateDao.create(new PayRate(
            2016, new BigDecimal(110), PLevel.SS),false);
        
        
        payRateDao.create(new PayRate(
            2015, new BigDecimal(73), PLevel.P1),false);
            
        payRateDao.create(new PayRate(
            2015, new BigDecimal(93), PLevel.P2),false);
            
        payRateDao.create(new PayRate(
            2015, new BigDecimal(115), PLevel.P3),false);
            
        payRateDao.create(new PayRate(
            2015, new BigDecimal(165), PLevel.P4),false);
            
        payRateDao.create(new PayRate(
            2015, new BigDecimal(205), PLevel.P5),false);
            
        payRateDao.create(new PayRate(
            2015, new BigDecimal(105), PLevel.DS),false);
            
        payRateDao.create(new PayRate(
            2015, new BigDecimal(105), PLevel.SS),false);
        
        
        payRateDao.create(new PayRate(
            2014, new BigDecimal(70), PLevel.P1),false);
            
        payRateDao.create(new PayRate(
            2014, new BigDecimal(90), PLevel.P2),false);
            
        payRateDao.create(new PayRate(
            2014, new BigDecimal(110), PLevel.P3),false);
            
        payRateDao.create(new PayRate(
            2014, new BigDecimal(160), PLevel.P4),false);
            
        payRateDao.create(new PayRate(
            2014, new BigDecimal(200), PLevel.P5),false);
            
        payRateDao.create(new PayRate(
            2014, new BigDecimal(100), PLevel.DS),false);
            
        payRateDao.create(new PayRate(
            2014, new BigDecimal(100), PLevel.SS),false);
        
        
        payRateDao.create(new PayRate(
            2013, new BigDecimal(67), PLevel.P1),false);
            
        payRateDao.create(new PayRate(
            2013, new BigDecimal(87), PLevel.P2),false);
            
        payRateDao.create(new PayRate(
            2013, new BigDecimal(105), PLevel.P3),false);
            
        payRateDao.create(new PayRate(
            2013, new BigDecimal(155), PLevel.P4),false);
            
        payRateDao.create(new PayRate(
            2013, new BigDecimal(195), PLevel.P5),false);
            
        payRateDao.create(new PayRate(
            2013, new BigDecimal(95), PLevel.DS),false);
            
        payRateDao.create(new PayRate(
            2013, new BigDecimal(95), PLevel.SS),false);
        
        
        payRateDao.create(new PayRate(
            2012, new BigDecimal(65), PLevel.P1),false);
            
        payRateDao.create(new PayRate(
            2012, new BigDecimal(85), PLevel.P2),false);
            
        payRateDao.create(new PayRate(
            2012, new BigDecimal(102), PLevel.P3),false);
            
        payRateDao.create(new PayRate(
            2012, new BigDecimal(150), PLevel.P4),false);
            
        payRateDao.create(new PayRate(
            2012, new BigDecimal(190), PLevel.P5),false);
            
        payRateDao.create(new PayRate(
            2012, new BigDecimal(93), PLevel.DS),false);
            
        payRateDao.create(new PayRate(
            2012, new BigDecimal(93), PLevel.SS),false);
        
        
        payRateDao.create(new PayRate(
            2011, new BigDecimal(60), PLevel.P1),false);
            
        payRateDao.create(new PayRate(
            2011, new BigDecimal(80), PLevel.P2),false);
            
        payRateDao.create(new PayRate(
            2011, new BigDecimal(100), PLevel.P3),false);
            
        payRateDao.create(new PayRate(
            2011, new BigDecimal(145), PLevel.P4),false);
            
        payRateDao.create(new PayRate(
            2011, new BigDecimal(185), PLevel.P5),false);
            
        payRateDao.create(new PayRate(
            2011, new BigDecimal(90), PLevel.DS),false);
            
        payRateDao.create(new PayRate(
            2011, new BigDecimal(90), PLevel.SS),false);
    }

    private void generateProjects() {
        Date issueDate = setDate(1, 1, 2014);
        
        projectDao.create(new Project(
        		12345, "Apollo", issueDate, null, new BigDecimal(1000), new BigDecimal(0), new BigDecimal(500000)),false);
        
        issueDate = setDate(11, 1, 2013);
        
        projectDao.create(new Project(
        		55522, "Barbosa", issueDate, null, new BigDecimal(2000), new BigDecimal(250000), new BigDecimal(1500000)),false);
        
        issueDate = setDate(7, 31, 2012);
        
        projectDao.create(new Project(
        		99777, "Carolina", issueDate, null, new BigDecimal(2000), new BigDecimal(10000000), new BigDecimal(40000000)),false);
        
        issueDate = setDate(2, 1, 2014);
        
        projectDao.create(new Project(
        		88999, "Davenport", issueDate, null, new BigDecimal(2000), new BigDecimal(30000), new BigDecimal(3000000)),false);
    }

    private void generateWorkPackages() {
        List<Effort> beginningEstimate = new ArrayList<Effort>();
        List<Effort> test = new ArrayList<Effort>();
        test.add(new Effort(PLevel.P1, 20));
        beginningEstimate.add(effortDao.read(1));
        beginningEstimate.add(effortDao.read(2));
        beginningEstimate.add(effortDao.read(3));
        beginningEstimate.add(effortDao.read(4));
        beginningEstimate.add(effortDao.read(5));

        String workPackageNumber = "";
        String wpName = "Lots'o Packages";
        String appendNumber = "";
        Date issueDate = setDate(1, 2, 2014);
        Date endDate = setDate(5, 15, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A111222", "Implement domain models", issueDate, "100", endDate, 12345, test),false);

		// Create 200 workpackages for Project Barbosa
//        for(int i = 0; i < 200; i++) {
//            if(i < 10) { workPackageNumber = "A11110" + i;}
//            if(i >= 10 && i < 100) { workPackageNumber = "A1111" + i; }
//            if(i >= 100) { workPackageNumber = "A111" + i; }
//            workPackageDao.create(new WorkPackage(
//                workPackageNumber, wpName, issueDate, "100", endDate, 55522, beginningEstimate),false);
//        }

        issueDate = setDate(2, 5, 2014);
        endDate = setDate(4, 12, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B333222", "Write tests", issueDate, "100", endDate, 12345, beginningEstimate),false);
        
        issueDate = setDate(1, 14, 2014);
        endDate = setDate(4, 17, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B333223", "Something important", issueDate, "0", endDate, 12345, beginningEstimate),false);
        
        issueDate = setDate(11, 10, 2013);
        endDate = setDate(11, 28, 2013);

        workPackageDao.create(new WorkPackage(
        		"A333444", "Design Database", issueDate, "100", endDate, 55522, beginningEstimate),false);
        
        issueDate = setDate(11, 12, 2013);
        endDate = setDate(12, 8, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"C333222", "Preliminary front end design", issueDate, "100", endDate, 55522, beginningEstimate),false);
        
        issueDate = setDate(11, 15, 2013);
        endDate = setDate(2, 5, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A333445", "Implement login logic", issueDate, "100", endDate, 55522, beginningEstimate),false);
        
        issueDate = setDate(8, 2, 2012);
        endDate = setDate(3, 27, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ33422", "Research technologies", issueDate, "0", endDate, 99977, beginningEstimate),false);
        
        issueDate = setDate(3, 15, 2013);
        endDate = setDate(6, 23, 2017);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ33423", "Solve world hunger", issueDate, "0", endDate, 99977, beginningEstimate),false);
    }

    private void generateWorkPackageAssignments() {

        List<WorkPackage> packages = workPackageDao.getAll();
        int qUserId = userDao.getAll().get(0).getId();
        int jedwardUserId = userDao.getAll().get(1).getId();
        int awongUserId = userDao.getAll().get(2).getId();
        int bnelsonUserId = userDao.getAll().get(3).getId();
        int csandhuUserId = userDao.getAll().get(4).getId();
        int zcantwellUserId = userDao.getAll().get(5).getId();
        int cparkUserId = userDao.getAll().get(6).getId();
        int gsmithUserId = userDao.getAll().get(7).getId();

        for(int i = 0; i < 5; i++)
        {
            workPackageAssignmentDao.create(new WorkPackageAssignment(
                    packages.get(0).getWorkPackageNumber(),
                    1,
                    true, true,
                    new Date(),
                    new Date()
            ),false);
            
            Date activateDate = setDate(1, 2, 2014);
            Date deactivateDate = setDate(5, 15, 2014);
            
            WorkPackageAssignment assignment = new WorkPackageAssignment("A111222", qUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("A111222", jedwardUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("A111222", awongUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("A111222", bnelsonUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("A111222", csandhuUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("A111222", zcantwellUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("A111222", cparkUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("A111222", gsmithUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(2, 6, 2014);
            deactivateDate = setDate(4, 12, 2014);
            
            assignment = new WorkPackageAssignment("B333222", qUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(1, 14, 2014);
            deactivateDate = setDate(4, 17, 2014);
            
            assignment = new WorkPackageAssignment("B333223", qUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("B333223", jedwardUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("B333223", awongUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("B333223", bnelsonUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("B333223", csandhuUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("B333223", zcantwellUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("B333223", cparkUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            assignment = new WorkPackageAssignment("B333223", gsmithUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(11, 10, 2013);
            deactivateDate = setDate(11, 28, 2013);
            
            assignment = new WorkPackageAssignment("A333444", awongUserId, true, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(11, 12, 2014);
            deactivateDate = setDate(12, 8, 2014);
            
            assignment = new WorkPackageAssignment("C333222", awongUserId, true, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
        }

    }
    private void generateProjectAssignments()
    {
        List<Project> projects = projectDao.getAll();
        List<User> users = userDao.getAll();

        /*
        for(int i = 0; i < 5; i++)
        {
            projectAssignmentDao.create(new ProjectAssignment(
                    projects.get(i).getProjectNumber(),
                    users.get(i).getId(),
                    false
            ));
        }
        */
        projectAssignmentDao.create(new ProjectAssignment(
                projects.get(0).getProjectNumber(),
                users.get(0).getId(),
                true, true
        ),false);
        
        ProjectAssignment assignment = new ProjectAssignment(12345, users.get(0).getId(), true, true);
        projectAssignmentDao.create(assignment, false);
        
        assignment = new ProjectAssignment(55522, users.get(1).getId(), true, true);
        projectAssignmentDao.create(assignment, false);
        
        assignment = new ProjectAssignment(99777, users.get(1).getId(), false, true);
        projectAssignmentDao.create(assignment, false);
    }
    
    private void generateTimesheets() {
    	TimesheetRow wp1Row1 = new TimesheetRow(12345, "A111222", 80, 80, 80, 80, 80, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row2 = new TimesheetRow(12345, "B333222", 80, 0, 0, 0, 0, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row3 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(1).getpLevel());
        
        TimesheetRow wp2Row1 = new TimesheetRow(55522, "A333444", 0, 65, 25, 35, 0, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp2Row2 = new TimesheetRow(55522, "C333222", 50, 0, 0, 25, 80, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp2Row3 = new TimesheetRow(55522, "A333445", 30, 0, 50, 0, 0, 40, 0, null,
                userDao.read(1).getpLevel());
        
        TimesheetRow wp3Row1 = new TimesheetRow(99777, "ZZ33422", 25, 0, 75, 0, 40, 60, 20, null,
                userDao.read(2).getpLevel());
        TimesheetRow wp3Row2 = new TimesheetRow(99777, "ZZ33423", 70, 20, 5, 0, 40, 30, 60, null,
                userDao.read(2).getpLevel());
        
        List<TimesheetRow> rowCollection = new ArrayList<TimesheetRow>();

        Timesheet tempTimesheet;
        
        rowCollection.add(wp1Row1);
        tempTimesheet = new Timesheet(1, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
        timesheetDao.create(tempTimesheet,false);
        rowCollection.clear();
        
        rowCollection.add(wp1Row2);
        rowCollection.add(wp1Row3);
        tempTimesheet = new Timesheet(1, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
        timesheetDao.create(tempTimesheet,false);

//        rowCollection = new ArrayList<TimesheetRow>();
//        rowCollection.add(wp1Row1);
//        rowCollection.add(wp2Row1);
//        tempTimesheet = new Timesheet(2, rowCollection, 12, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(3, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(4, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(5, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(6, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(7, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(8, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(1, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(2, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(3, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(4, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(5, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(6, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(7, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        tempTimesheet = new Timesheet(8, new ArrayList(rowCollection), 13, 2014, 0, false, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        rowCollection.clear();
//
//        rowCollection.add(wp2Row1);
//        rowCollection.add(wp2Row2);
//        rowCollection.add(wp2Row3);
//        tempTimesheet = new Timesheet(1, new ArrayList(rowCollection), 47, 2013, 0, true, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        rowCollection.clear();
//
//        rowCollection.add(wp3Row1);
//        rowCollection.add(wp3Row2);
//        tempTimesheet = new Timesheet(2, new ArrayList(rowCollection), 11, 2014, 0, true, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        rowCollection.clear();
//
//        // Rudy: adding new sample timesheet data
//        // User2, week 13
//        TimesheetRow wp3Row3 = new TimesheetRow(99777, "ZZ334222", 25, 25, 75, 0, 40, 60, 20, null,
//                userDao.read(2).getpLevel());
//        rowCollection.add(wp3Row3);
//        tempTimesheet = new Timesheet(2, new ArrayList(rowCollection), 13, 2014, 0, true, true, true);
//        timesheetDao.create(tempTimesheet,false);
//        rowCollection.clear();
        
    }

    private void generateWorkPackageStatusReports(){

        List<Effort> remainingEstimate= effortDao.listOfEffort(99, 101);
        Date date = setDate(3, 24, 2014);
        workPackageStatusReportDao.create(new WorkPackageStatusReport(12, 2014, date, "new wpsr", "Lots of work accomplished",
                "none", "approve timesheets", remainingEstimate, "none", "B333223"),false);
        
        remainingEstimate = effortDao.listOfEffort(99, 101);
        date = setDate(3, 31, 2014);
        workPackageStatusReportDao.create(new WorkPackageStatusReport(13, 2014, date, "WPSR to test", "Pretty much slacked off",
        		"Too many to count", "So many things", remainingEstimate, "Bugs galore", "B333223"), false);
    }

    private Date setDate(int month, int day, int year)
    {
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DATE, day);
    	c.set(Calendar.MONTH, month - 1);
    	c.set(Calendar.YEAR, year);
    	Date theDate = c.getTime();
    	return theDate;
    }

    private void generateEffort() {
        effortDao.create(new Effort(PLevel.P1, 50), true);
        effortDao.create(new Effort(PLevel.P2, 50), true);
        effortDao.create(new Effort(PLevel.P3, 50), true);
        effortDao.create(new Effort(PLevel.P4, 50), true);
        effortDao.create(new Effort(PLevel.P5, 50), true);
        effortDao.create(new Effort(PLevel.DS, 50), true);
        effortDao.create(new Effort(PLevel.SS, 50), true);
        effortDao.create(new Effort(PLevel.P1, 100), true);
        effortDao.create(new Effort(PLevel.P2, 100), true);
        effortDao.create(new Effort(PLevel.P3, 100), true);
        effortDao.create(new Effort(PLevel.P4, 100), true);
        effortDao.create(new Effort(PLevel.P5, 100), true);
        effortDao.create(new Effort(PLevel.DS, 100), true);
        effortDao.create(new Effort(PLevel.SS, 100), true);
        effortDao.create(new Effort(PLevel.P1, 2300), true);
        effortDao.create(new Effort(PLevel.P2, 2400), true);
        effortDao.create(new Effort(PLevel.P3, 2500), true);
        effortDao.create(new Effort(PLevel.P4, 2600), true);
        effortDao.create(new Effort(PLevel.P5, 2700), true);
        effortDao.create(new Effort(PLevel.DS, 2800), true);
        effortDao.create(new Effort(PLevel.SS, 2900), true);
    }
}
