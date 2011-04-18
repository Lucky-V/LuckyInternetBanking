package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CurrencyRateDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CurrentCurrencyRateDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrentCurrencyRate;
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
public class CurrencyRateSessionBean {

    @EJB
    private CurrencyRateDao currencyRateDao;

    @EJB
    private CurrentCurrencyRateDao currentCurrencyRateDao;

    public List<CurrencyRate> getAllRates() {
        return this.currencyRateDao.findAll();
    }

    public void update(CurrencyRate c) {
        try {
            this.currencyRateDao.update(c);
        } catch (EntityAlreadyUpdatedException ex) {
            Logger.getLogger(CurrencyRateSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void remove(Integer id) {
        this.currencyRateDao.deleteById(id);
    }

    public CurrencyRate getByCurrencyCode(String code) {
        return this.currencyRateDao.getByCurrencyCode(code);
    }

    public CurrencyRate getById(Integer id) {
        return this.currencyRateDao.findById(id);
    }

    public void updateCurrentCurrency(CurrentCurrencyRate ccr) {
        try {
            this.currentCurrencyRateDao.update(ccr);
        } catch (EntityAlreadyUpdatedException ex) {
            Logger.getLogger(CurrencyRateSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CurrentCurrencyRate getCurrentRateByCurrencyCode(String code) {
        return this.currentCurrencyRateDao.getCurrentRateByCurrencyCode(code);
    }

    public void removeCurrentCurrencyRate(Integer id) {
        this.currentCurrencyRateDao.deleteById(id);
    }
}
