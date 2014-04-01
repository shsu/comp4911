package ca.bcit.infosys.comp4911.access;

import ca.bcit.infosys.comp4911.domain.Effort;
import ca.bcit.infosys.comp4911.helper.ValidationHelper;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by craigleclair on 2014-03-29.
 */
@Stateless
public class EffortDao {
    @PersistenceContext(unitName = "comp4911")
    private EntityManager em;

    public void create (final Effort effort, final boolean validate)
    {
        if (!validate || ValidationHelper.validateEntity(effort)) {
            em.persist(effort);
        }
    }

    public Effort read (final int effortID)
    {
        return em.find(Effort.class, effortID);
    }

    public void update (final Effort effort)
    {
        if(ValidationHelper.validateEntity(effort)){
            em.merge(effort);
        }
    }
}
