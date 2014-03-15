package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.domain.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import java.math.BigDecimal;
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
        //generateTimesheets();
    }

    private void generateUsers() {
        userDao.create(new User(
                "q","q","Bruce","Link",new Date(),true,"MIA",40,0,0,0,0,0,0, PLevel.P5
        ));

        for(int i = 0; i < 5; i++)
        {
            userDao.create(new User(
                    "username" + i + "@example.com",
                    "password",
                    "firstName" + i,
                    "lastName" + i,
                    new Date(),
                    false,
                    "",
                    40,0,0,0,0,0,0, PLevel.P1
                    ));
        }
        
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
        endDate = setDate(2, 12, 2014);
        
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
        
        issueDate = setDate(12, 15, 2013);
        endDate = setDate(2, 5, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A3334452", "Implement login logic", issueDate, "100", endDate, 55522, 45000));
        
        issueDate = setDate(8, 2, 2012);
        endDate = setDate(3, 12, 2013);
        
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
    /**
    private void generateTimesheets() {

        Timesheet timesheet;

        for(int j = 0; j < 2; j++)
        {
            timesheet = new Timesheet(j, j, j, j, j, false, false);

            timesheetDao.create(timesheet);
            List<TimesheetRow> rows = timesheet.getTimesheetRows();
            for(int i = 0; i < 5; i++)
            {
                TimesheetRow temp = new TimesheetRow(i, i, i, i, i, i, i, i, i, "hi" + i);
                timesheetRowDao.create(temp);
                temp.setTimesheet(timesheet);
                rows.add(temp);
            }
        }

        }

    }
    */
    
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
