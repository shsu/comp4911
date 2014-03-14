package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.UserPayRateHistory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserPayRateHistoryDao {

    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create (final UserPayRateHistory payRateHistory)
    {
        em.persist(payRateHistory);
    }

    public UserPayRateHistory read ( final int payRateHistoryID)
    {
        return em.find(UserPayRateHistory.class, payRateHistoryID);
    }

    public void update (final UserPayRateHistory payRateHistory)
    {
        em.merge(payRateHistory);
    }

    public void delete (final UserPayRateHistory payRateHistory)
    {
        em.remove(read(payRateHistory.getId()));
    }

    public List<UserPayRateHistory> getAllUserPayRateHistories() {
        TypedQuery<UserPayRateHistory> query = em.createQuery("select p from UserPayRateHistory p",
                UserPayRateHistory.class);
        return query.getResultList();
    }

    public List<UserPayRateHistory> getAllUserPayRateHistoryByUser(final int userId) {
        TypedQuery<UserPayRateHistory> query = em.createQuery("select p from UserPayRateHistory p where p.userId = :userId ",
                UserPayRateHistory.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
