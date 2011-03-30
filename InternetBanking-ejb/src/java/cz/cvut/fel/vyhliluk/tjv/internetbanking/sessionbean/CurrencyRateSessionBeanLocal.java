package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrentCurrencyRate;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lucky
 */
@Local
public interface CurrencyRateSessionBeanLocal {

    CurrencyRate getById(Integer id);

    CurrencyRate getByCurrencyCode(String code);

    List<CurrencyRate> getAllRates();

    void update(CurrencyRate r);

    void remove(Integer id);

    void updateCurrentCurrency(CurrentCurrencyRate ccr);

    CurrentCurrencyRate getCurrentRateByCurrencyCode(String code);

    void removeCurrentCurrencyRate(Integer id);
    
}
