package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.User;
import com.google.common.base.Optional;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
public class UserDao {

    @PersistenceContext(unitName = "comp4911")
    EntityManager em;

    public void create(final User user) {
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

    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("select u from User u",
          User.class);
        return query.getResultList();
    }

    public Optional<User> authenticate(final String username, final String password){
        for (User user : getAll()) {
            if (user.getUsername().equals(username)){
                if(BCrypt.checkpw(password, user.getPassword())){
                    return Optional.of(user);
                } else {
                    return Optional.absent();
                }
            }
        }

        return Optional.absent();
    }
}