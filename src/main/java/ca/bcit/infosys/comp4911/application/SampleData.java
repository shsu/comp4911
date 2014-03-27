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

    public SampleData() {
    }

    @PostConstruct
    public void populateData() {
        generateUsers();
        generateProjects();
        generatePayRates();
        generateProjectAssignments();
        generateWorkPackages();
        generateWorkPackageAssignments();
        generateTimesheets();
        generateWorkPackageStatusReports();
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
        
        String workPackageNumber = "";
        String wpName = "Lots'o Packages";
        String appendNumber = "";
        Date issueDate = setDate(1, 2, 2014);
        Date endDate = setDate(1, 10, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A1112222", "Implement domain models", issueDate, "100", endDate, 12345, 100000,0),false);

        for(int i = 0; i < 1000; i++) {
            if(i < 10) { workPackageNumber = "A111100" + i;}
            if(i >= 10 && i < 100) { workPackageNumber = "A11110" + i; }
            if(i >= 100) { workPackageNumber = "A1111" + i; }
            workPackageDao.create(new WorkPackage(
                workPackageNumber, wpName, issueDate, "100", endDate, 55522, 100000,0),false);
        }

        issueDate = setDate(2, 5, 2014);
        endDate = setDate(4, 12, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B3332222", "Write tests", issueDate, "100", endDate, 12345, 50000,0),false);
        
        issueDate = setDate(1, 14, 2014);
        endDate = setDate(4, 17, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B3332223", "Something important", issueDate, "0", endDate, 12345, 350000,0),false);
        
        issueDate = setDate(11, 10, 2013);
        endDate = setDate(11, 28, 2013);

        workPackageDao.create(new WorkPackage(
        		"A3334444", "Design Database", issueDate, "100", endDate, 55522, 25000,0),false);
        
        issueDate = setDate(11, 12, 2013);
        endDate = setDate(12, 8, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"C3332222", "Preliminary front end design", issueDate, "100", endDate, 55522, 100000,0),false);
        
        issueDate = setDate(11, 15, 2013);
        endDate = setDate(2, 5, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A3334452", "Implement login logic", issueDate, "100", endDate, 55522, 45000,0),false);
        
        issueDate = setDate(8, 2, 2012);
        endDate = setDate(3, 27, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ334222", "Research technologies", issueDate, "0", endDate, 99977, 5000000,0),false);
        
        issueDate = setDate(3, 15, 2013);
        endDate = setDate(6, 23, 2017);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ334225", "Solve world hunger", issueDate, "0", endDate, 99977, 25000000,0),false);
    }

    private void generateWorkPackageAssignments() {

        List<WorkPackage> packages = workPackageDao.getAll();
        int qUserId = userDao.getAll().get(0).getId();
        int awongUserId = userDao.getAll().get(2).getId();

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
            Date deactivateDate = setDate(1, 10, 2014);
            
            WorkPackageAssignment assignment = new WorkPackageAssignment("A1112222", qUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(2, 6, 2014);
            deactivateDate = setDate(4, 12, 2014);
            
            assignment = new WorkPackageAssignment("B3332222", qUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(1, 14, 2014);
            deactivateDate = setDate(4, 17, 2014);
            
            assignment = new WorkPackageAssignment("B3332223", qUserId, false, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(11, 10, 2013);
            deactivateDate = setDate(11, 28, 2013);
            
            assignment = new WorkPackageAssignment("A3334444", awongUserId, true, true, activateDate, deactivateDate);
            workPackageAssignmentDao.create(assignment, false);
            
            activateDate = setDate(11, 12, 2014);
            deactivateDate = setDate(12, 8, 2014);
            
            assignment = new WorkPackageAssignment("C3332222", awongUserId, true, true, activateDate, deactivateDate);
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
    	TimesheetRow wp1Row1 = new TimesheetRow(12345, "A1112222", 80, 80, 80, 80, 80, 40, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row2 = new TimesheetRow(12345, "B3332222", 80, 0, 0, 0, 0, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row3 = new TimesheetRow(12345, "B3332223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(1).getpLevel());
        
        TimesheetRow wp2Row1 = new TimesheetRow(55522, "A3334444", 0, 65, 25, 35, 0, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp2Row2 = new TimesheetRow(55522, "C3332222", 50, 0, 0, 25, 80, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp2Row3 = new TimesheetRow(55522, "A3334452", 30, 0, 50, 0, 0, 40, 0, null,
                userDao.read(1).getpLevel());
        
        TimesheetRow wp3Row1 = new TimesheetRow(99777, "ZZ334222", 25, 0, 75, 0, 40, 60, 20, null,
                userDao.read(2).getpLevel());
        TimesheetRow wp3Row2 = new TimesheetRow(99777, "ZZ334229", 70, 20, 5, 0, 40, 30, 60, null,
                userDao.read(2).getpLevel());
        
        List<TimesheetRow> rowCollection = new ArrayList<TimesheetRow>();

        Timesheet tempTimesheet;
        
        rowCollection.add(wp1Row1);
        tempTimesheet = new Timesheet(1, new ArrayList(rowCollection), 11, 2014, 40, false, true, true);
        timesheetDao.create(tempTimesheet,false);
        rowCollection.clear();
        
        rowCollection.add(wp1Row2);
        rowCollection.add(wp1Row3);
        tempTimesheet = new Timesheet(1, new ArrayList(rowCollection), 12, 2014, 0, false, true, true);
        timesheetDao.create(tempTimesheet,false);
        rowCollection.clear();
        
        rowCollection.add(wp2Row1);
        rowCollection.add(wp2Row2);
        rowCollection.add(wp2Row3);
        tempTimesheet = new Timesheet(1, new ArrayList(rowCollection), 47, 2013, 0, true, true, true);
        timesheetDao.create(tempTimesheet,false);
        rowCollection.clear();
        
        rowCollection.add(wp3Row1);
        rowCollection.add(wp3Row2);
        tempTimesheet = new Timesheet(2, new ArrayList(rowCollection), 11, 2014, 0, true, true, true);
        timesheetDao.create(tempTimesheet,false);
        rowCollection.clear();
    }

    private void generateWorkPackageStatusReports(){
        Date date = setDate(1, 1, 2014);
        Set<Effort> effort = new HashSet<Effort>();
        workPackageStatusReportDao.create(new WorkPackageStatusReport(1, 2014, date, "new wpsr", "Lots of work accomplished",
                "none", "approve timesheets", effort, "none", "A1112222"),false);
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
}
