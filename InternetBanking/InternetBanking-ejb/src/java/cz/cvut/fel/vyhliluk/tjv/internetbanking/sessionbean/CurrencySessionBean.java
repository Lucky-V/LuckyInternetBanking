package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CurrencyDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
@RolesAllowed(value="Manager")
public class CurrencySessionBean {

    @EJB
    private CurrencyDao currencyDao;

    @RolesAllowed(value={"Manager", "Customer"})
    public List<Currency> getAllCurencies() {
        return this.currencyDao.findAll();
    }

    public List<Currency> getCurrenciesWithRate() {
        return this.currencyDao.getCurrenciesWithRate();
    }

    public void updateCurrency(Currency c) {
        try {
            this.currencyDao.update(c);
        } catch (EntityAlreadyUpdatedException ex) {
            Logger.getLogger(CurrencySessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteCurrency(String code) {
        this.currencyDao.deleteById(code);
    }

    public Currency getByCode(String code) {
        return this.currencyDao.findById(code);
    }
}
