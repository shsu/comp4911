package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.User;
import ca.bcit.infosys.comp4911.helper.SH;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserDao {

    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create(final User user, final boolean validate) {
        if (!validate || ValidationHelper.validateEntity(user)) {
            if(!doesUsernameExist(user.getUsername())){
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                em.persist(user);
            }
            else {
                SH.responseBadRequest("Username exists already!");
            }
        }
    }

    public User read(final int id) {
        return em.find(User.class, id);
    }

    public void update(final User user) {
        if(ValidationHelper.validateEntity(user)){

            String oldPassword = read(user.getId()).getPassword();
            if (user.getPassword() != oldPassword) {
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            }

            em.merge(user);
        }
    }

    public void delete(final User user) {
        em.remove(read(user.getId()));
    }

    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("select u from User u",
          User.class);
        return query.getResultList();
    }

    public Optional<User> authenticate(final String username, final String password) {
        for (User user : getAll()) {
            if (user.getUsername().equals(username)) {
                if (BCrypt.checkpw(password, user.getPassword())) {
                    return Optional.of(user);
                } else {
                    return Optional.absent();
                }
            }
        }

        return Optional.absent();
    }

    public List<User> getAllPeons(int supervisorUserID) {
        TypedQuery<User> query = em.createQuery("select u from User u " +
                "Where u.supervisorUserID = :supervisorUserID", User.class);

        query.setParameter("supervisorUserID", supervisorUserID);
        return query.getResultList();
    }

    public boolean isSupervisor(int userId) {
        TypedQuery<User> query = em.createQuery("select u from User u " +
                "where u.supervisorUserID = :userId", User.class);
        query.setParameter("userId", userId);
        List<User> peons = query.getResultList();
        if(peons.size() > 0) { return true; }
        return false;
    }

    public boolean isTimesheetApprover(int userId) {
        TypedQuery<User> query = em.createQuery("select u from User u " +
                "where u.timesheetApproverUserID = :userId", User.class);
        query.setParameter("userId", userId);
        List<User> peons = query.getResultList();
        if(peons.size() > 0) { return true; }
        return false;
    }

    public boolean doesUsernameExist(String username) {
        TypedQuery<User> query = em.createQuery("select u from User u" +
                " where u.username = :username", User.class);
        query.setParameter("username", username);
        if(query.getResultList().size() > 0 ) { return true; }
        return false;
    }

}
