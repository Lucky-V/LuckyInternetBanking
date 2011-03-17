/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Bank;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lucky
 */
@Local
public interface BankSessionBeanLocal {

    Bank getByCode(Integer code);

    void updateBankCode(Bank b);

    List<Bank> getAllBanks();

    void removeBank(Integer code);
    
}
