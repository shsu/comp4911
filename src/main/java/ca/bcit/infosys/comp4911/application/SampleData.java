package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.*;
import ca.bcit.infosys.comp4911.domain.*;
import org.joda.time.DateTime;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.math.BigDecimal;
import java.util.*;
import java.util.Random;

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
        generateUsers(100);  // generates n regular users, n/5 HR, n/4 managers
        generatePayRates();
        generateEffort();
        generateProjects();
        generate100Projects();
        generateProjectAssignments();

        generateWorkPackages();
        generateWorkPackageAssignments();
        generateWorkPackageAssignmentForAll();

        generateTimesheets();
        //andJobsSaidLetThereBeAShitTonOfTimeSheets();

        generateWorkPackageStatusReports();
        projectWPSRGenerator(55522);

        //testProject();
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
                34567890, 0, 0, PLevel.P3),false);
        
        userDao.create(new User(
                "csandhu@example.com", "q", "Christine", "Sandhu", startDate, true, "Active", 40, 0, 14,
                45678901, 1, 1, PLevel.P4),false);
        
        userDao.create(new User(
                "zcantwell@example.com", "q", "Zamir", "Cantwell", startDate, false, "Active", 40, 0, 14,
                56789012, 0, 0, PLevel.P5),false);
        
        userDao.create(new User(
                "cpark@example.com", "q", "Chi-En", "Park", startDate, false, "Active", 40, 0, 14,
                67890123, 1, 1, PLevel.SS),false);
        
        userDao.create(new User(
                "gsmith@example.com", "q", "Grace", "Smith", startDate, false, "Active", 40, 0, 14,
                78901234, 0, 0, PLevel.DS),false);
        
        userDao.create(new User(
                "w", "w", "Mark", "Ahmadi", startDate, false, "Active", 40, 0, 14,
                2, 0, 0, PLevel.P5),false);
    }

    /*
    * @Param
    * n is number of engineer users to be generated.
    * */
    private void generateUsers(int n) {
        // Loops to create up to 2000 users for testing
        // Separate loops for HR, Engineers, and Managers
        //
        String hr;   // hr
        String e;     // engineer
        String s;     // supervisor

        // Supervisors
        for(int i = 1; i <= n/4; i++){
            s = "s" + Integer.toString(i);
            userDao.create(new User(
                    s, s, s, s, new Date(), true, "Active", 40, 50, 0, 0, 0, 0, PLevel.P5),false);
        }

        // HR users
        for(int i = 1; i <= n/5; i++){
            hr = "hr" + Integer.toString(i);
            userDao.create(new User(
                    hr, hr, hr, hr, new Date(), true, "Active", 40, 50, 0, 0, 0, 0, PLevel.P4),false);
        }

        // Engineers
        for(int i = 1; i <= n; i++) {
            e = "e" + Integer.toString(i);
            userDao.create(new User(
                    e, e, e, e, 1), false);
        }


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
        		12345, "Apollo", issueDate, null, new BigDecimal(1.2), new BigDecimal(0), new BigDecimal(500000)),false);
        
        issueDate = setDate(11, 1, 2013);
        
        projectDao.create(new Project(
        		55522, "Barbosa", issueDate, null, new BigDecimal(1.6), new BigDecimal(250000), new BigDecimal(1500000)),false);
        
        issueDate = setDate(7, 31, 2012);
        
        projectDao.create(new Project(
        		99777, "Carolina", issueDate, null, new BigDecimal(2), new BigDecimal(10000000), new BigDecimal(40000000)),false);
        
        issueDate = setDate(2, 1, 2014);
        
        projectDao.create(new Project(
        		88999, "Davenport", issueDate, null, new BigDecimal(2), new BigDecimal(30000), new BigDecimal(3000000)),false);


    }

    private void generate100Projects() {
        Date issueDate = setDate(1, 1, 2014);
        String pr;

        for(int i = 10000; i <= 11100; i++){
            pr = "Project" + Integer.toString(i);
            projectDao.create(new Project(
                    i, pr, issueDate, null, new BigDecimal(1.2), new BigDecimal(0), new BigDecimal(500000)),false);
        }
    }

    private void generateWorkPackages() {

        String workPackageNumber = "";
        String wpName = "Lots'o Packages";
        Date issueDate = setDate(1, 2, 2014);
        Date endDate = setDate(5, 15, 2014);

        //Base example
        workPackageDao.create(new WorkPackage(
        		"A111222", "Implement domain models", issueDate, true, endDate, 12345, effortGenerator(10,10,10,10,10,10,10)),false);


        /**
         * Please fix this generator. Some must have been changed as it now generates duplicate work package
         * numbers. 
         */
		// Create 200 workpackages for Project Barbosa
        for(int i = 0; i <= 200; i++) {
            if(i < 10) { workPackageNumber = "A11111" + i;}                 // add 1 digit
            if(i >= 10 && i < 100) { workPackageNumber = "A2222" + i; }     // add 2 digits
            if(i >= 100) {

                if((i > 100 && i < 110)){
                    continue;
                }
                workPackageNumber = "A333" + i;
            }                // add 3 digits
            workPackageDao.create(new WorkPackage(
                workPackageNumber, wpName, issueDate, true, endDate, 55522, effortGenerator(10,10,10,10,10,10,10)),false);
        }

        issueDate = setDate(2, 5, 2014);
        endDate = setDate(4, 12, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B333222", "Write tests", issueDate, true, endDate, 12345, effortGenerator(10,10,10,10,10,10,10)),false);
        
        issueDate = setDate(1, 14, 2014);
        endDate = setDate(4, 17, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"B333223", "Something important", issueDate, true, endDate, 12345, effortGenerator(10,10,10,10,10,10,10)),false);
        
        issueDate = setDate(11, 10, 2013);
        endDate = setDate(11, 28, 2013);

        workPackageDao.create(new WorkPackage(
        		"A333444", "Design Database", issueDate, true, endDate, 55522, effortGenerator(10,10,10,10,10,10,10)),false);
        
        issueDate = setDate(11, 12, 2013);
        endDate = setDate(12, 8, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"C333222", "Preliminary front end design", issueDate, true, endDate, 55522, effortGenerator(10,10,10,10,10,10,10)),false);
        
        issueDate = setDate(11, 15, 2013);
        endDate = setDate(2, 5, 2014);
        
        workPackageDao.create(new WorkPackage(
        		"A333445", "Implement login logic", issueDate, true, endDate, 55522, effortGenerator(10,10,10,10,10,10,10)),false);
        
        issueDate = setDate(8, 2, 2012);
        endDate = setDate(3, 27, 2013);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ33422", "Research technologies", issueDate, true, endDate, 99977, effortGenerator(10,10,10,10,10,10,10)),false);
        
        issueDate = setDate(3, 15, 2013);
        endDate = setDate(6, 23, 2017);
        
        workPackageDao.create(new WorkPackage(
        		"ZZ33423", "Solve world hunger", issueDate, true, endDate, 99977, effortGenerator(10,10,10,10,10,10,10)),false);
    }

    private void generateWorkPackageAssignments() {

    	int qUserId = userDao.getAll().get(0).getId();
        int jedwardUserId = userDao.getAll().get(1).getId();
        int awongUserId = userDao.getAll().get(2).getId();
        int bnelsonUserId = userDao.getAll().get(3).getId();
        int csandhuUserId = userDao.getAll().get(4).getId();
        int zcantwellUserId = userDao.getAll().get(5).getId();
        int cparkUserId = userDao.getAll().get(6).getId();
        int gsmithUserId = userDao.getAll().get(7).getId();
          
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
        
        assignment = new WorkPackageAssignment("C333222", awongUserId, true, true, activateDate, deactivateDate);
        workPackageAssignmentDao.create(assignment, false);        
        
    }
    
    /**
     * For each project assignment, assign all child workpackages to the assigned user
     */
    private void generateWorkPackageAssignmentForAll(){
        Date activateDate = setDate(2, 12, 2014);
        Date deactivateDate = setDate(12, 8, 2014);
        WorkPackageAssignment assignment;
        List<WorkPackage> wpList;
        List<ProjectAssignment> paList = projectAssignmentDao.getAll();
        for(ProjectAssignment pa: paList){
        	wpList = workPackageDao.getAllByProject(pa.getProjectNumber());
        	for(WorkPackage wp : wpList){
        		//assign only if not already assigned
            	if(workPackageAssignmentDao.getByUserAndWorkPackage(wp.getWorkPackageNumber(), pa.getUserId()).isEmpty()){
            		assignment = new WorkPackageAssignment(wp.getWorkPackageNumber(), pa.getUserId(), false , true, activateDate, deactivateDate);
            		workPackageAssignmentDao.create(assignment, false); 
            	}
        	}
        }
    }
    
    private void generateProjectAssignments()
    {
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
        
        ProjectAssignment assignment = new ProjectAssignment(12345, users.get(0).getId(), true, true);
        projectAssignmentDao.create(assignment, false);
        
        assignment = new ProjectAssignment(55522, users.get(1).getId(), true, true);
        projectAssignmentDao.create(assignment, false);
        
        assignment = new ProjectAssignment(99777, users.get(1).getId(), false, true);
        projectAssignmentDao.create(assignment, false);        
        
    }
    
    /**
     * Assigning one random project to each user
     */
    private void generateProjectAssignmentForAll(){      
        List<Project> projectList = projectDao.getAll();
        List<User> userList = userDao.getAll();
        ProjectAssignment assignment;
        int pCount = projectList.size();
        int pNumber;
        boolean isAssigned;
        Random rand = new Random();
        for(User u : userList){  		
        	isAssigned = false;
        	pNumber = rand.nextInt(pCount);
        	//check if the project is already assigned to the user        	
        	for(ProjectAssignment pa :projectAssignmentDao.getAllByUserId(u.getId())){
        		if(pa.getProjectNumber()==pNumber){
        			isAssigned = true; 
        		}
        	}        	
        	if(!isAssigned){
	        	assignment = new ProjectAssignment(projectList.get(pNumber).getProjectNumber(), u.getId(), false, true);        	   
	    		projectAssignmentDao.create(assignment, false);      
        	}
        }
    }
    
    private void generateTimesheets() {
        /**
         * Please sanitize data here. Please note that once you assign a Timesheet row to a Timesheet that it cannot
         * be assigned to assigned to another Timesheet.
         */
        TimesheetRow wp1Row1 = new TimesheetRow(12345, "A111222", 80, 80, 80, 80, 80, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row1C2 = new TimesheetRow(12345, "A111222", 80, 80, 80, 80, 80, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row2 = new TimesheetRow(12345, "B333222", 80, 0, 0, 0, 0, 0, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row3u1wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(1).getpLevel());
        
        TimesheetRow wp1Row3u2wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(2).getpLevel());
        TimesheetRow wp1Row3u3wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(3).getpLevel());
        TimesheetRow wp1Row3u4wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(4).getpLevel());
        TimesheetRow wp1Row3u5wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(5).getpLevel());
        TimesheetRow wp1Row3u6wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(6).getpLevel());
        TimesheetRow wp1Row3u7wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(7).getpLevel());
        TimesheetRow wp1Row3u8wpsr1 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(8).getpLevel());
        TimesheetRow wp1Row3u1wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(1).getpLevel());
        TimesheetRow wp1Row3u2wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(2).getpLevel());
        TimesheetRow wp1Row3u3wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(3).getpLevel());
        TimesheetRow wp1Row3u4wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(4).getpLevel());
        TimesheetRow wp1Row3u5wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(5).getpLevel());
        TimesheetRow wp1Row3u6wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(6).getpLevel());
        TimesheetRow wp1Row3u7wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(7).getpLevel());
        TimesheetRow wp1Row3u8wpsr2 = new TimesheetRow(12345, "B333223", 0, 50, 60, 30, 50, 30, 0, null,
                userDao.read(8).getpLevel());
        
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
        tempTimesheet = new Timesheet(1, rowCollection, 12, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u1wpsr1);
        tempTimesheet = new Timesheet(1, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u2wpsr1);
        tempTimesheet = new Timesheet(2, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u3wpsr1);
        tempTimesheet = new Timesheet(3, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u4wpsr1);
        tempTimesheet = new Timesheet(4, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u5wpsr1);
        tempTimesheet = new Timesheet(5, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u6wpsr1);
        tempTimesheet = new Timesheet(6, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u7wpsr1);
        tempTimesheet = new Timesheet(7, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u8wpsr1);
        tempTimesheet = new Timesheet(8, rowCollection, 13, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u1wpsr2);
        tempTimesheet = new Timesheet(1, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u2wpsr2);
        Timesheet t = new Timesheet(2, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(t,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u3wpsr2);
        Timesheet t2 = new Timesheet(3, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(t2,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u4wpsr2);
        tempTimesheet = new Timesheet(4, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u5wpsr2);
        tempTimesheet = new Timesheet(5, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u6wpsr2);
        tempTimesheet = new Timesheet(6, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u7wpsr2);
        tempTimesheet = new Timesheet(7, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row3u8wpsr2);
        tempTimesheet = new Timesheet(8, rowCollection, 14, 2014, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);

        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp2Row1);
        rowCollection.add(wp2Row2);
        rowCollection.add(wp2Row3);
        tempTimesheet = new Timesheet(1, rowCollection, 47, 2013, 0, true, true, false);
        timesheetDao.create(tempTimesheet,false);

        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp3Row1);
        rowCollection.add(wp3Row2);
        tempTimesheet = new Timesheet(2, rowCollection, 12, 2014, 0, true, true, true);
        timesheetDao.create(tempTimesheet,false);
        
        rowCollection = new ArrayList<TimesheetRow>();
        rowCollection.add(wp1Row1C2);
        tempTimesheet = new Timesheet(1, rowCollection, 14, 2014, 0, false, true, false);
        timesheetDao.create(tempTimesheet,false);

    }

    private void generateWorkPackageStatusReports(){
        /**
         * As with work packages please use the effortGenerator to generate a list of effort
         */
        List<Effort> remainingEstimate= effortDao.listOfEffort(99, 101);
        Date date = setDate(3, 24, 2014);
        WorkPackageStatusReport WPSR = new WorkPackageStatusReport(date, "new wpsr", "Lots of work accomplished",
                "none", "approve timesheets", remainingEstimate, "none", "B333223");
        workPackageStatusReportDao.create(WPSR,false);

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

    /**
     * Please replace any effort which is used above in the generateWorkPackageStatusReports and the generateWorkPackage
     * methods with the effortGenerator method at the bottom of the page. Effort should only be
     * being persisted via cascading in Work Packages, and Work Package Status reports. Delete this method and its
     * contents when you are finished.
     */
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

    private List<Effort> effortGenerator(int p1, int p2, int p3, int p4, int p5, int ss, int ds){
        List<Effort> effortList = new ArrayList<Effort>();
        effortList.add(new Effort(PLevel.P1, p1));
        effortList.add(new Effort(PLevel.P2, p2));
        effortList.add(new Effort(PLevel.P3, p3));
        effortList.add(new Effort(PLevel.P4, p4));
        effortList.add(new Effort(PLevel.P5, p5));
        effortList.add(new Effort(PLevel.SS, ss));
        effortList.add(new Effort(PLevel.SS, ds));
        return effortList;

    }

    private void andJobsSaidLetThereBeAShitTonOfTimeSheets(){
        Random random = new Random();
        boolean approved = true;
        boolean signed = true;
        boolean pending = false;
        for(int i = 0; i < 7000; i++){
            if(i > 3000 && i < 6000){
                approved = true;
                signed =  true;
                pending = false;
            }
            String wpNumber = "A3331";
            wpNumber += (random.nextInt(9)+1);
            wpNumber += (random.nextInt(9)+1);
            List<TimesheetRow> tsrList = generateListOfRows(55522, wpNumber, random.nextInt(4)+1);
            timesheetDao.create(new Timesheet(random.nextInt(8)+1, tsrList, random.nextInt(52)+1, 2014,
                    0, approved, signed, pending), false);
        }
    }

    private List<TimesheetRow> generateListOfRows(int projectNumber, String wpNumber, int numOfRows){
        List<TimesheetRow> tsr = new ArrayList<TimesheetRow>();
        Random random = new Random();
        for(int i = 0; i < numOfRows; i++){
            tsr.add(new TimesheetRow(projectNumber, wpNumber, random.nextInt(3),
                    random.nextInt(3),
                    random.nextInt(3),
                    random.nextInt(3),
                    random.nextInt(3),
                    random.nextInt(3),
                    random.nextInt(3), "Note", getRandPLevel(random.nextInt(7))));
        }
        return tsr;
    }

    private PLevel getRandPLevel(int i) {
        switch(i) {
            case 0:
                return PLevel.P1;
            case 1:
                return PLevel.P2;
            case 2:
                return PLevel.P3;
            case 3:
                return PLevel.P4;
            case 4:
                return PLevel.P5;
            case 5:
                return PLevel.DS;
            case 6:
                return PLevel.SS;

        }
        return PLevel.P1;
    }

    /**
     * Iterates through all work packages of a given project. It then creates a random(0-4) number of work
     * package status reports for each work package in the project. Effort may or may not be increased for continuing
     * work package status reports.
     * @param projectNumber
     */
    private void projectWPSRGenerator(int projectNumber){
        Random random = new Random();
        int numOfWPSRsPerWP;
        int increase;
        int effortAmount;
        Date date;
        List<Effort> estimatedEffort;
        List<WorkPackage> projectWPs = workPackageDao.getAllByProject(projectNumber);
        Iterator<WorkPackage> projWPIterator = projectWPs.listIterator();
        WorkPackage currentWp;
        Calendar calendar = Calendar.getInstance();
        while(projWPIterator.hasNext()){
            int month = random.nextInt(12);
            int day = random.nextInt(27);
            int year = 2014;
            currentWp = projWPIterator.next();
            numOfWPSRsPerWP = random.nextInt(5);
            increase = random.nextInt(2);
            effortAmount = random.nextInt(80)+20;
            for(int i = 0; i < numOfWPSRsPerWP; i++){
                if(i > 0){
                    if(month >= 11 ){
                        month = 0;
                        year += 1;
                        calendar.set(year, month, day);
                    }
                    else{
                        month += 1;
                        calendar.set(year, month, day);
                    }

                }
                if(increase > 0 && i > 0){
                    effortAmount += 10;
                }
                date = calendar.getTime();
                estimatedEffort = effortGenerator(effortAmount, effortAmount, effortAmount, effortAmount,
                        effortAmount, effortAmount, effortAmount);
                workPackageStatusReportDao.create(new WorkPackageStatusReport(date, "Test", "Test",
                        "Test", "Test", estimatedEffort, "Test", currentWp.getWorkPackageNumber()), false);
            }
        }
    }

    private void testProject() {
        List<User> allSystemUsers = userDao.getAll();
        for(User u : allSystemUsers){
            projectAssignmentDao.create(new ProjectAssignment(88999, u.getId()), false);
        }
        User u = new User("cleclair@example.com", "password", "Craig", "LeClair", 1);
        userDao.create(u, false);
        projectAssignmentDao.create(new ProjectAssignment(88999, u.getId()), false);
        List<ProjectAssignment> pas = projectAssignmentDao.getAllByUserId(u.getId());
        pas.get(0).setProjectManager(true);
        projectAssignmentDao.update(pas.get(0));


        workPackageDao.create(new WorkPackage(
                    "C100000", "Level 1", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C110000", "Level 2", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111000", "Level 3", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111100", "Level 4", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111110", "Level 5", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111111", "Level 6", 88999, effortGenerator(100,100,100,100,100,100,100)), false);
        timesheetsForLeaf(2, "C111111", 4);
        timesheetsForLeaf(2, "C111111", 4);
        timesheetsForLeaf(2, "C111111", 4);
        timesheetsForLeaf(2, "C111111", 4);
        timesheetsForLeaf(2, "C111111", 4);
        timesheetsForLeaf(2, "C111111", 4);
        workPackageDao.create(new WorkPackage(
                "C111112", "Level 6", 88999, effortGenerator(200,200,420,420,420,420,420)), false);
        timesheetsForLeaf(2, "C111112", 4);
        timesheetsForLeaf(2, "C111112", 4);
        timesheetsForLeaf(2, "C111112", 4);
        timesheetsForLeaf(2, "C111112", 4);
        timesheetsForLeaf(2, "C111112", 4);
        timesheetsForLeaf(2, "C111112", 5);
        workPackageDao.create(new WorkPackage(
                "C111113", "Level 6", 88999, effortGenerator(210,210,210,210,210,210,210)), false);
        timesheetsForLeaf(3, "C111113", 5);
        timesheetsForLeaf(3, "C111113", 5);
        timesheetsForLeaf(3, "C111113", 5);
        timesheetsForLeaf(2, "C111113", 5);
        timesheetsForLeaf(2, "C111113", 5);
        timesheetsForLeaf(2, "C111113", 5);
        workPackageDao.create(new WorkPackage(
                "C111114", "Level 6", 88999, effortGenerator(110,110,110,110,110,110,110)), false);
        timesheetsForLeaf(4, "C111114", 5);
        timesheetsForLeaf(4, "C111114", 5);
        timesheetsForLeaf(2, "C111114", 5);
        workPackageDao.create(new WorkPackage(
                "C111115", "Level 6", 88999, effortGenerator(110,110,110,110,110,110,110)), false);
        timesheetsForLeaf(2, "C111115", 2);
        timesheetsForLeaf(2, "C111115", 2);
        timesheetsForLeaf(2, "C111115", 2);
        timesheetsForLeaf(2, "C111115", 2);
        timesheetsForLeaf(2, "C111115", 2);
        workPackageDao.create(new WorkPackage(
                "C111116", "Level 6", 88999, effortGenerator(110,110,110,110,110,110,110)), false);
        timesheetsForLeaf(2, "C111116", 2);
        timesheetsForLeaf(2, "C111116", 2);
        workPackageDao.create(new WorkPackage(
                "C111200", "Level 4", 88999, effortGenerator(750,870,690,510,310,210,110)), false);
        timesheetsForLeaf(2, "C111200", 2);
        workPackageDao.create(new WorkPackage(
                "C111300", "Level 4", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111310", "Level 5", 88999, effortGenerator(750,870,690,510,310,210,110)), false);
        timesheetsForLeaf(4, "C111310", 5);
        timesheetsForLeaf(4, "C111310", 7);
        timesheetsForLeaf(5, "C111310", 2);
        workPackageDao.create(new WorkPackage(
                "C111320", "Level 5", 88999, effortGenerator(750,870,690,510,310,210,110)), false);
        timesheetsForLeaf(4, "C111320", 3);
        timesheetsForLeaf(4, "C111320", 7);
        timesheetsForLeaf(5, "C111320", 5);
        workPackageDao.create(new WorkPackage(
                "C111330", "Level 5", 88999, effortGenerator(750,870,690,510,310,210,110)), false);
        timesheetsForLeaf(4, "C111330", 3);
        timesheetsForLeaf(4, "C111330", 7);
        timesheetsForLeaf(5, "C111330", 5);
        workPackageDao.create(new WorkPackage(
                "C111400", "Level 4", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111410", "Level 5", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111411", "Level 6", 88999, effortGenerator(2100,2100,2100,2100,2100,2100,2100)), false);
        timesheetsForLeaf(4, "C111411", 3);
        timesheetsForLeaf(4, "C111411", 7);
        timesheetsForLeaf(5, "C111411", 5);
        workPackageDao.create(new WorkPackage(
                "C111412", "Level 6", 88999, effortGenerator(1100,1100,1100,2100,2100,2100,2100)), false);
        timesheetsForLeaf(4, "C111412", 3);
        timesheetsForLeaf(4, "C111412", 7);
        timesheetsForLeaf(5, "C111412", 5);
        workPackageDao.create(new WorkPackage(
                "C111413", "Level 6", 88999, effortGenerator(1100,1100,1100,1100,1100,1100,1100)), false);
        timesheetsForLeaf(3, "C111413", 3);
        timesheetsForLeaf(7, "C111413", 2);
        timesheetsForLeaf(5, "C111413", 1);
        workPackageDao.create(new WorkPackage(
                "C111414", "Level 6", 88999, effortGenerator(1100,1100,2100,1100,4100,3100,2100)), false);
        timesheetsForLeaf(3, "C111414", 3);
        timesheetsForLeaf(7, "C111414", 2);
        timesheetsForLeaf(5, "C111414", 1);
        workPackageDao.create(new WorkPackage(
                "C111415", "Level 6", 88999, effortGenerator(1100,2100,2100,1100,2100,1100,1100)), false);
        timesheetsForLeaf(3, "C111415", 3);
        timesheetsForLeaf(7, "C111415", 2);
        timesheetsForLeaf(5, "C111415", 4);
        workPackageDao.create(new WorkPackage(
                "C111416", "Level 6", 88999, effortGenerator(1100,1100,3100,5100,4100,3100,2100)), false);
        timesheetsForLeaf(3, "C111416", 3);
        timesheetsForLeaf(7, "C111416", 2);
        timesheetsForLeaf(5, "C111416", 4);
        timesheetsForLeaf(3, "C111416", 3);
        timesheetsForLeaf(7, "C111416", 2);
        workPackageDao.create(new WorkPackage(
                "C111420", "Level 5", 88999), false);
        workPackageDao.create(new WorkPackage(
                "C111421", "Level 6", 88999, effortGenerator(3100,1100,1100,1100,1100,1100,1100)), false);
        timesheetsForLeaf(3, "C111421", 3);
        timesheetsForLeaf(7, "C111421", 2);
        timesheetsForLeaf(5, "C111421", 4);
        timesheetsForLeaf(3, "C111421", 3);
        timesheetsForLeaf(7, "C111421", 2);
        workPackageDao.create(new WorkPackage(
                "C111422", "Level 6", 88999, effortGenerator(1200,1100,1100,1100,1100,1100,1100)), false);
        timesheetsForLeaf(4, "C111422", 3);
        timesheetsForLeaf(6, "C111422", 3);
        timesheetsForLeaf(5, "C111422", 4);
        timesheetsForLeaf(3, "C111422", 3);
        timesheetsForLeaf(3, "C111422", 1);
        workPackageDao.create(new WorkPackage(
                "C111423", "Level 6", 88999, effortGenerator(1300,1400,1100,1100,1200,1100,1200)), false);
        timesheetsForLeaf(3, "C111423", 3);
        timesheetsForLeaf(2, "C111423", 2);
        timesheetsForLeaf(5, "C111423", 4);
        timesheetsForLeaf(5, "C111423", 3);
        timesheetsForLeaf(6, "C111423", 2);
        workPackageDao.create(new WorkPackage(
                "C111424", "Level 6", 88999, effortGenerator(1100,1300,1100,1300,1500,1300,1100)), false);
        timesheetsForLeaf(3, "C111424", 3);
        timesheetsForLeaf(2, "C111424", 2);
        timesheetsForLeaf(5, "C111424", 4);
        workPackageDao.create(new WorkPackage(
                "C111425", "Level 6", 88999, effortGenerator(1100,1100,1200,1400,1100,1030,1010)), false);
        timesheetsForLeaf(3, "C111425", 3);
        timesheetsForLeaf(2, "C111425", 2);
        timesheetsForLeaf(5, "C111425", 4);
        workPackageDao.create(new WorkPackage(
                "C111426", "Level 6", 88999, effortGenerator(1100,1040,1030,1020,1010,1300,1010)), false);
        timesheetsForLeaf(3, "C111426", 3);
        timesheetsForLeaf(2, "C111426", 2);
        timesheetsForLeaf(5, "C111426", 4);
        workPackageDao.create(new WorkPackage(
                "C111427", "Level 6", 88999, effortGenerator(1200,1400,1500,1600,1010,1200,1040)), false);
        timesheetsForLeaf(3, "C111427", 3);
        timesheetsForLeaf(2, "C111427", 2);
        timesheetsForLeaf(5, "C111427", 4);
        workPackageDao.create(new WorkPackage(
                "C111500", "Level 5", 88999), false);
        
     // Adding the leaf WPs to a list
        List<String> leafsWPNumber = new ArrayList<String>();
        leafsWPNumber.add("C111111");
        leafsWPNumber.add("C111112");
        leafsWPNumber.add("C111113");
        leafsWPNumber.add("C111114");
        leafsWPNumber.add("C111115");
        leafsWPNumber.add("C111116");
        leafsWPNumber.add("C111200");
        leafsWPNumber.add("C111310");
        leafsWPNumber.add("C111320");
        leafsWPNumber.add("C111330");
        leafsWPNumber.add("C111411");
        leafsWPNumber.add("C111412");
        leafsWPNumber.add("C111413");
        leafsWPNumber.add("C111414");
        leafsWPNumber.add("C111415");
        leafsWPNumber.add("C111416");
        leafsWPNumber.add("C111421");
        leafsWPNumber.add("C111422");
        leafsWPNumber.add("C111423");
        leafsWPNumber.add("C111424");
        leafsWPNumber.add("C111425");
        leafsWPNumber.add("C111426");
        leafsWPNumber.add("C111427");
        
        
//        // Assign the leaf work packages to all users
//        for (String wpNumber : leafsWPNumber) {
//        	assignWPToAllUsers(wpNumber);
//        }
        
        // Assign leaf work packages to appropriate users
        assignWPToOneUser("C111111", 2);
        assignWPToOneUser("C111112", 2);
        assignWPToOneUser("C111113", 2);
        assignWPToOneUser("C111113", 3);
        assignWPToOneUser("C111114", 2);
        assignWPToOneUser("C111114", 4);
        assignWPToOneUser("C111115", 2);
        assignWPToOneUser("C111116", 2);
        assignWPToOneUser("C111200", 2);
        assignWPToOneUser("C111310", 4);
        assignWPToOneUser("C111310", 5);
        assignWPToOneUser("C111320", 4);
        assignWPToOneUser("C111320", 5);
        assignWPToOneUser("C111330", 4);
        assignWPToOneUser("C111330", 5);
        assignWPToOneUser("C111411", 4);
        assignWPToOneUser("C111411", 5);
        assignWPToOneUser("C111412", 4);
        assignWPToOneUser("C111412", 5);
        assignWPToOneUser("C111413", 3);
        assignWPToOneUser("C111413", 5);
        assignWPToOneUser("C111413", 7);
        assignWPToOneUser("C111414", 3);
        assignWPToOneUser("C111414", 5);
        assignWPToOneUser("C111414", 7);
        assignWPToOneUser("C111415", 3);
        assignWPToOneUser("C111415", 5);
        assignWPToOneUser("C111415", 7);
        assignWPToOneUser("C111416", 3);
        assignWPToOneUser("C111416", 5);
        assignWPToOneUser("C111416", 7);
        assignWPToOneUser("C111421", 3);
        assignWPToOneUser("C111421", 5);
        assignWPToOneUser("C111421", 7);
        assignWPToOneUser("C111422", 3);
        assignWPToOneUser("C111422", 4);
        assignWPToOneUser("C111422", 5);
        assignWPToOneUser("C111422", 6);
        assignWPToOneUser("C111423", 2);
        assignWPToOneUser("C111423", 3);
        assignWPToOneUser("C111423", 5);
        assignWPToOneUser("C111423", 6);
        assignWPToOneUser("C111424", 2);
        assignWPToOneUser("C111424", 3);
        assignWPToOneUser("C111424", 5);
        assignWPToOneUser("C111425", 2);
        assignWPToOneUser("C111425", 3);
        assignWPToOneUser("C111425", 5);
        assignWPToOneUser("C111426", 2);
        assignWPToOneUser("C111426", 3);
        assignWPToOneUser("C111426", 5);
        assignWPToOneUser("C111427", 2);
        assignWPToOneUser("C111427", 3);
        assignWPToOneUser("C111427", 5);
        
        
        // Creating the status reports for the leafs
        generateStatusReport("C111111", setDate(4, 14, 2014), effortGenerator(100,100,100,100,0,100,100));
        generateStatusReport("C111111", setDate(5, 14, 2014), effortGenerator(50,50,50,50,0,50,50));
        generateStatusReport("C111111", setDate(6, 14, 2014), effortGenerator(20,20,20,20,0,20,20));
        
        generateStatusReport("C111112", setDate(4, 14, 2014), effortGenerator(200,200,420,420,220,420,420));
        generateStatusReport("C111112", setDate(5, 14, 2014), effortGenerator(150,150,300,300,150,300,300));
        generateStatusReport("C111112", setDate(6, 14, 2014), effortGenerator(100,100,200,200,100,200,150));
        
        generateStatusReport("C111113", setDate(4, 14, 2014), effortGenerator(30,210,210,210,30,210,210));
        generateStatusReport("C111113", setDate(5, 14, 2014), effortGenerator(0,200,200,150,0,150,150));
        generateStatusReport("C111113", setDate(6, 14, 2014), effortGenerator(0,0,0,0,0,0,10));
        
        generateStatusReport("C111114", setDate(4, 14, 2014), effortGenerator(110,0,110,110,0,110,110));
        generateStatusReport("C111114", setDate(5, 14, 2014), effortGenerator(50,0,50,50,0,50,50));
        generateStatusReport("C111114", setDate(6, 14, 1014), effortGenerator(10,0,10,10,0,10,10));
        
        generateStatusReport("C111115", setDate(4, 14, 2014), effortGenerator(110,110,110,110,10,110,110));
        generateStatusReport("C111115", setDate(5, 14, 2014), effortGenerator(50,50,50,50,0,50,50));
        generateStatusReport("C111115", setDate(6, 14, 2014), effortGenerator(10,10,10,10,0,10,10));
        
        generateStatusReport("C111116", setDate(4, 14, 2014), effortGenerator(200,200,420,420,220,420,420));
        generateStatusReport("C111116", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111116", setDate(6, 14, 2014), effortGenerator(0,0,0,0,10,0,0));
        
        generateStatusReport("C111200", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111200", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111200", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111310", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111310", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111310", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111320", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111320", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111320", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111330", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111330", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111330", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111411", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111411", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111411", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111412", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111412", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111412", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111413", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111413", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111413", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111414", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111414", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111414", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111415", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111415", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111415", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111416", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111416", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111416", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111421", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111421", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111421", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111422", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111422", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111422", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111423", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111423", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111423", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111424", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111424", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111424", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111425", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111425", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111425", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111426", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111426", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111426", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        generateStatusReport("C111427", setDate(4, 14, 2014), effortGenerator(200,200,200,200,200,200,200));
        generateStatusReport("C111427", setDate(5, 14, 2014), effortGenerator(100,100,100,100,100,100,100));
        generateStatusReport("C111427", setDate(6, 14, 2014), effortGenerator(20,20,20,20,20,20,20));
        
        
    }

    private void timesheetsForLeaf(int userId, String wPNumber, int hoursPerDay){
        List<TimesheetRow> tsrList = generateListOfRows2(88999, wPNumber, hoursPerDay, userId);
        timesheetDao.create(new Timesheet(userId, tsrList, 23, 2014,
                0, true, true, false), false);
    }

    private void generateStatusReport(String wpNumber, Date reportDate, List<Effort> remainingEstimate) {
    	workPackageStatusReportDao.create(
    			new WorkPackageStatusReport(
    					reportDate, 
    					"Report", 
    					"Implemented cute features",
    					"No problems", 
    					"Implement cuter features", 
    					remainingEstimate, 
    					"None anticipated", 
    					wpNumber
    			), 
    			false
    	);
    }
    
    private void assignWPToAllUsers(String wpNumber) {
    	List<User> allSystemUsers = userDao.getAll();
    	Date wpIssueDate = workPackageDao.read(wpNumber).getIssueDate(); 
    	
    	for (User user : allSystemUsers) {
    		workPackageAssignmentDao.create(
    				new WorkPackageAssignment(
    						wpNumber, 
    						user.getId(),
    						false,
    						true,
    						wpIssueDate,
    						null
    				), 
    				false
    		);
    	}
    }
    
    private void assignWPToOneUser(String wpNumber, int userId) {
    	User user = userDao.read(userId);
    	Date wpIssueDate = workPackageDao.read(wpNumber).getIssueDate(); 
    	
		workPackageAssignmentDao.create(
				new WorkPackageAssignment(
						wpNumber,
						userId,
						false,
						true,
						wpIssueDate,
						null
				),
				false
		);
    	
    }
    
    
    /*
     *  Modification of generateListOfRows
     */
    private List<TimesheetRow> generateListOfRows2(int projectNumber, String wpNumber, int numOfRows, int userId){
    	List<TimesheetRow> tsr = new ArrayList<TimesheetRow>();
        Random random = new Random();
        for(int i = 0; i < numOfRows; i++){
            tsr.add(new TimesheetRow(projectNumber, wpNumber, 
            		10,
                    10,
                    10,
                    10,
                    10,
                    0,
                    0, 
                    "Note", 
                    userDao.read(userId).getpLevel()
            ));
        }
        return tsr;
    }
    
}
