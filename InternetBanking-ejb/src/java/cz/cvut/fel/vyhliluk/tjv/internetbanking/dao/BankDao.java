
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Bank;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class BankDao extends AbstractDao<Bank, Integer> {

    public BankDao() {
        super(Bank.class);
    }
    
    
 
}
