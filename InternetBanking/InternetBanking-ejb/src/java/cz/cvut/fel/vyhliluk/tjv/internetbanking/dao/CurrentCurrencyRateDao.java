package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrentCurrencyRate;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class CurrentCurrencyRateDao extends AbstractDao<CurrentCurrencyRate, Integer> {

    public CurrentCurrencyRateDao() {
        super(CurrentCurrencyRate.class);
    }

    public CurrentCurrencyRate getCurrentRateByCurrencyCode(String code) {
        Query q = this.em.createNamedQuery("CurrentCurrencyRate.getByCurrencyCode");
        q.setParameter("code", code);
        try {
            return (CurrentCurrencyRate) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
