
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
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
public class CurrencyRateDao extends AbstractDao<CurrencyRate, Integer> {

    public CurrencyRateDao() {
        super(CurrencyRate.class);
    }

    public CurrencyRate getByCurrencyCode(String code) {
        Query q = this.em.createNamedQuery("CurrencyRate.getByCurrencyCode");
        q.setParameter("code", code);
        try {
            return (CurrencyRate) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    
 
}
