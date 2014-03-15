package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.domain.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        //generateWorkPackageAssignments();
        generateTimesheets();
    }

    private void generateUsers() {
        userDao.create(new User(
                "q","q","Bruce","Link",new Date(),true,"MIA",40,0,0,0,24,0,0, PLevel.P5
        ));
        userDao.create(new User(
          "username0@example.com","password","Bruce","Link",new Date(),true,"MIA",40,0,0,0,0,0,0, PLevel.P5
        ));
        
        Date startDate = setDate(1, 1, 2000);
        
        userDao.create(new User(
        		"jedward", "q", "John", "Edward", startDate, false, "Active", 40, 0, 0, 14,
        		12345678, 56789012, 56789012, PLevel.P1));
        
        userDao.create(new User(
                "awong", "q", "Alice", "Wong", startDate, false, "Active", 40, 0, 0, 14,
                23456789, 56789012, 56789012, PLevel.P2));
        
        userDao.create(new User(
                "bnelson", "q", "Bob", "Nelson", startDate, false, "Active", 40, 0, 0, 14,
                34567890, 56789012, 56789012, PLevel.P3));
        
        userDao.create(new User(
                "csandhu", "q", "Christine", "Sandhu", startDate, true, "Active", 40, 0, 0, 14,
                45678901, 56789012, 56789012, PLevel.P4));
        
        userDao.create(new User(
                "zcantwell", "q", "Zamir", "Cantwell", startDate, false, "Active", 40, 0, 0, 14,
                56789012, 0, 0, PLevel.P5));
        
        userDao.create(new User(
                "cpark", "q", "Chi-En", "Park", startDate, false, "Active", 40, 0, 0, 14,
                67890123, 56789012, 56789012, PLevel.SS));
        
        userDao.create(new User(
                "gsmith", "q", "Grace", "Smith", startDate, false, "Active", 40, 0, 0, 14,
                78901234, 56789012, 56789012, PLevel.DS));
    }

    private void generatePayRates() {
        payRateDao.create(new PayRate(
            2014, new BigDecimal(70), PLevel.P1));
        payRateDao.create(new PayRate(
            2014, new BigDecimal(90), PLevel.P2));
        payRateDao.create(new PayRate(
            2014, new BigDecimal(110), PLevel.P3));
        payRateDao.create(new PayRate(
            2014, new BigDecimal(160), PLevel.P4));
        payRateDao.create(new PayRate(
            2014, new BigDecimal(200), PLevel.P5));
        payRateDao.create(new PayRate(
            2014, new BigDecimal(100), PLevel.DS));
        payRateDao.create(new PayRate(
            2014, new BigDecimal(100), PLevel.SS));

    }

    private void generateProjects() {
        Date issueDate = setDate(1, 1, 2014);
        
        projectDao.create(new Project(
        		12345, "Apollo", issueDate, null, new BigDecimal(1000), new BigDecimal(0), new BigDecimal(500000)));
        
        issueDate = setDate(11, 1, 2013);
        
        projectDao.create(new Project(
        		55522, "Barbosa", issueDate, null, new BigDecimal(2000), new BigDecimal(250000), new BigDecimal(1500000)));
        
        issueDate = setDate(7, 31, 2012);
        
        projectDao.create(new Project(
        		99777, "Carolina", issueDate, null, new BigDecimal(2000), new BigDecimal(10000000), new BigDecimal(40000000)));
        
        issueDate = setDate(2, 1, 2014);
        
        projectDao.create(new Project(
        		88999, "Davenport", issueDate, null, new BigDecimal(2000), new BigDecimal(30000), new BigDecimal(3000000)));
    }

    private void generateWorkPackages() {
        
        Date issueDate = setDate(1, 2, 2014);
        Date endDate = setDate(1, 10, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A1112222", "Implement domain models", issueDate, "100", endDate, 12345, 100000));
        
        issueDate = setDate(2, 5, 2014);
        endDate = setDate(4, 12, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B3332222", "Write tests", issueDate, "100", endDate, 12345, 50000));
        
        issueDate = setDate(1, 14, 2014);
        endDate = setDate(4, 17, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B3332223", "Something important", issueDate, "0", endDate, 12345, 350000));
        
        issueDate = setDate(11, 10, 2013);
        endDate = setDate(11, 28, 2013);

        workPackageDao.create(new WorkPackage(
        		"A3334444", "Design Database", issueDate, "100", endDate, 55522, 25000));
        
        issueDate = setDate(11, 12, 2013);
        endDate = setDate(12, 8, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"C3332222", "Preliminary front end design", issueDate, "100", endDate, 55522, 100000));
        
        issueDate = setDate(11, 15, 2013);
        endDate = setDate(2, 5, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A3334452", "Implement login logic", issueDate, "100", endDate, 55522, 45000));
        
        issueDate = setDate(8, 2, 2012);
        endDate = setDate(3, 27, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ334222", "Research technologies", issueDate, "0", endDate, 99977, 5000000));
        
        issueDate = setDate(3, 15, 2013);
        endDate = setDate(6, 23, 2017);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ334225", "Solve world hunger", issueDate, "0", endDate, 99977, 25000000));
    }

    private void generateWorkPackageAssignments() {

        List<WorkPackage> packages = workPackageDao.getAll();
        List<User> users = userDao.getAll();

        for(int i = 0; i < 5; i++)
        {
            workPackageAssignmentDao.create(new WorkPackageAssignment(
                    packages.get(0).getWorkPackageNumber(),
                    users.get(i).getId(),
                    false,
                    new Date(),
                    new Date()
            ));
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
                true
        ));
    }
    
    private void generateTimesheets() {
    	TimesheetRow wp1Row1 = new TimesheetRow(12345, "A1112222", 80, 80, 80, 80, 80, 40, 0, null);
        TimesheetRow wp1Row2 = new TimesheetRow(12345, "B3332222", 80, 0, 0, 0, 0, 0, 0, null);
        TimesheetRow wp1Row3 = new TimesheetRow(12345, "B3332223", 0, 50, 60, 30, 50, 30, 0, null);
        
        TimesheetRow wp2Row1 = new TimesheetRow(55522, "A3334444", 0, 65, 25, 35, 0, 0, 0, null);
        TimesheetRow wp2Row2 = new TimesheetRow(55522, "C3332222", 50, 0, 0, 25, 80, 0, 0, null);
        TimesheetRow wp2Row3 = new TimesheetRow(55522, "A3334452", 30, 0, 50, 0, 0, 40, 0, null);
        
        TimesheetRow wp3Row1 = new TimesheetRow(99777, "ZZ334222", 25, 0, 75, 0, 40, 60, 20, null);
        TimesheetRow wp3Row2 = new TimesheetRow(99777, "ZZ334229", 70, 20, 5, 0, 40, 30, 60, null);
        
        List<TimesheetRow> rowCollection = new ArrayList<TimesheetRow>();
        Timesheet tempTimesheet;
        
        rowCollection.add(wp1Row1);
        timesheetRowDao.create(wp1Row1);
        tempTimesheet = new Timesheet(12345678, rowCollection, 1, 2014, 0, 40, true, true);
        timesheetDao.create(tempTimesheet);
        rowCollection.clear();
        
        rowCollection.add(wp1Row2);
        rowCollection.add(wp1Row3);
        timesheetRowDao.create(wp1Row2);
        timesheetRowDao.create(wp1Row3);
        tempTimesheet = new Timesheet(23456789, rowCollection, 12, 2014, -90, 0, false, true);
        rowCollection.clear();
        
        rowCollection.add(wp2Row1);
        rowCollection.add(wp2Row2);
        rowCollection.add(wp2Row3);
        timesheetRowDao.create(wp2Row1);
        timesheetRowDao.create(wp2Row2);
        timesheetRowDao.create(wp2Row3);
        tempTimesheet = new Timesheet(34567890, rowCollection, 47, 2013, 0, 0, true, true);
        rowCollection.clear();
        
        rowCollection.add(wp3Row1);
        rowCollection.add(wp3Row2);
        timesheetRowDao.create(wp3Row1);
        timesheetRowDao.create(wp3Row2);
        tempTimesheet = new Timesheet(23456789, rowCollection, 11, 2014, 45, 0, true, true);
        rowCollection.clear();
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
