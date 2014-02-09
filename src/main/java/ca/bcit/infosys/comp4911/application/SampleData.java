package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.domain.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class SampleData {

    @EJB
    private UserDao userDao;

    @EJB
    private TimesheetDao timesheetDao;

    public SampleData() {
    }

    @PostConstruct
    public void populateData() {
        generateUsers();
        generateTimesheets();
    }

    private void generateUsers() {
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
        for (int i = 1; i < 100; i++) {
            userDao.create(new User("test" + i, hashedPassword,
              "test-firstName " + i, "test-lastName " + i));
        }
    }

    private void generateTimesheets() {
        User user = userDao.read(1);

        for (int i = 4; i < 20; i++) {
            timesheetDao.create(new Timesheet(i, 2014, 2, true, user));
            timesheetDao.create(new Timesheet(i, 2014, 3, true, user));
            timesheetDao.create(new Timesheet(i, 2014, 1, false, user));
            timesheetDao.create(new Timesheet(i, 2014, 4, false, user));
        }
    }
}
