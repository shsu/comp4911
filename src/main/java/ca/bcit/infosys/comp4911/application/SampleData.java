package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.ProjectAssignmentDao;
import ca.bcit.infosys.comp4911.access.ProjectDao;
import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.access.TimesheetRowDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.access.WorkPackageAssignmentDao;
import ca.bcit.infosys.comp4911.access.WorkPackageDao;
import ca.bcit.infosys.comp4911.domain.PayLevel;
import ca.bcit.infosys.comp4911.domain.Project;
import ca.bcit.infosys.comp4911.domain.ProjectAssignment;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.domain.TimesheetRow;
import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.domain.WorkPackage;
import ca.bcit.infosys.comp4911.domain.WorkPackageAssignment;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    ProjectDao projectDao;

    @EJB
    ProjectAssignmentDao projectAssignmentDao;

    @EJB
    WorkPackageAssignmentDao workPackageAssignmentDao;

    @EJB
    WorkPackageDao workPackageDao;

    public SampleData() {
    }

    @PostConstruct
    public void populateData() {
        generateUsers();
        generateProjects();
        generateProjectAssignments();
        generateWorkPackages();
        generateWorkPackageAssignments();
        //generateTimesheets();
    }

    private void generateUsers() {
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
        for(int i = 0; i < 5; i++)
        {
            userDao.create(new User(
                    "username" + i,
                    hashedPassword,
                    "firstName" + i,
                    "lastName" + i,
                    "employee" + i + "@example.com",
                    new Date(),
                    false,
                    "status" + i,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    40,
                    i,
                    i,
                    i
                    ));
        }

    }

    private void generateProjects() {

        for (int i = 0; i < 5; i++)
        {
            projectDao.create(new Project(
                "1234" + i,
                    "Project" + i,
                    new Date(),
                    new Date(),
                    null,
                    new BigDecimal(12.5),
                    new BigDecimal(1000 + i * i),
                    new BigDecimal(1000 + i)
                    )
            );
        }
    }

    private void generateWorkPackages() {
        List<Project> projects = projectDao.getAll();

        for (int i = 0; i < 5; i++)
        {
            workPackageDao.create(new WorkPackage(
                    projects.get(i).getProjectNumber() + i,
                    "WorkPackageName" + i,
                    projects.get(i),
                    new Date(),
                    new Date(),
                    new Date(),
                    "purpose" + i,
                    "description" + i,
                    "progressStatus" + i,
                    null,
                    100 + i
            ));
        }
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

        for(int i = 0; i < 5; i++)
        {
            projectAssignmentDao.create(new ProjectAssignment(
                    projects.get(i).getProjectNumber(),
                    users.get(i),
                    false
            ));
        }
    }

    private void generateTimesheets() {
/**
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
    */
    }

}
