package ca.bcit.infosys.comp4911.application;

import ca.bcit.infosys.comp4911.access.UserDao;
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

    public SampleData() {
    }

    @PostConstruct
    public void populateData() {
        String hashedPassword = BCrypt.hashpw("password", BCrypt.gensalt());
        for (int i = 1; i < 100; i++) {
            userDao.create(new User("test" + i, hashedPassword,
              "test-firstName " + i, "test-lastName " + i));
        }
    }
}
