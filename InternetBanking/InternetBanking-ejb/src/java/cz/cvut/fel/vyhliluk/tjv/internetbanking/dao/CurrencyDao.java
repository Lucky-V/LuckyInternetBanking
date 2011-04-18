
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class CurrencyDao extends AbstractDao<Currency, String> {

    public CurrencyDao() {
        super(Currency.class);
    }

    public List<Currency> getCurrenciesWithRate() {
        Query q = this.em.createNamedQuery("Currency.getWithRate");
        return q.getResultList();
    }
    
    
 
}
