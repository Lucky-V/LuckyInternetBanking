/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
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
    
}
