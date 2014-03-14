package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.User;
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

    public void create(final User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        em.persist(user);
    }

    public User read(final int id) {
        return em.find(User.class, id);
    }

    public void update(final User user) {
        em.merge(user);
    }

    public void delete(final User user) {
        em.remove(read(user.getId()));
    }

    public void updatePassword(final int id, final String newPassword) {
        User user = read(id);
        if (user != null && !Strings.isNullOrEmpty(newPassword)) {
            String oldPassword = user.getPassword();

            // If old password was modified, hash the new one.
            if (newPassword != oldPassword) {
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            }
        }
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

}
