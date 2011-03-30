/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lucky
 */
@Local
public interface CurrencySessionBeanLocal {

    Currency getByCode(String code);

    List<Currency> getAllCurencies();

    List<Currency> getCurrenciesWithRate();

    void updateCurrency(Currency c);

    void deleteCurrency(String code);
    
}
