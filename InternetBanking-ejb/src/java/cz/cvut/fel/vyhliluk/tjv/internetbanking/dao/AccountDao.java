
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class AccountDao extends AbstractDao<Account, Long> {

    public AccountDao() {
        super(Account.class);
    }
    
    
 
}
