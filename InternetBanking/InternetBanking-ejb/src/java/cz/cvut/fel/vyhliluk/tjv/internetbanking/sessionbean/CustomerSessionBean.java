
package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.AccountDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
@RolesAllowed("Customer")
public class CustomerSessionBean {
    
    @EJB
    private AccountDao accountDao;

    @EJB
    private BankTransactionDao bankTransDao;

    public List<BankTransaction> getTransactions(Long accountId, Customer loggedCustomer) {
        Account acc = this.accountDao.findById(accountId);
        if (!loggedCustomer.equals(acc.getCustomer())) {
            throw new SecurityException("Not your account!!!");
        }

        return this.bankTransDao.getTransByAccount(acc);
    }
 
}
