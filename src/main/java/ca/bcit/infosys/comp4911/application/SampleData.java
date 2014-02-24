package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.TimesheetDao;
import ca.bcit.infosys.comp4911.access.TimesheetRowDao;
import ca.bcit.infosys.comp4911.access.UserDao;
import ca.bcit.infosys.comp4911.domain.PayLevel;
import ca.bcit.infosys.comp4911.domain.Timesheet;
import ca.bcit.infosys.comp4911.domain.TimesheetRow;
import ca.bcit.infosys.comp4911.domain.User;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.transaction.Transactional;
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

    public SampleData() {
    }

    @PostConstruct
    public void populateData() {
        generateUsers();
        generateTimesheets();
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
