package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.AccountDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CustomerDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
@RolesAllowed(value = {"Manager"})
public class AccountSessionBean {

    private static final Logger LOG = LoggerFactory.getLogger(AccountSessionBean.class);
    @EJB
    private CustomerDao custDao;
    @EJB
    private AccountDao accountDao;
    @EJB
    private BankTransactionDao bankTransDao;

    public Account getById(Long id) {
        return this.accountDao.findById(id);
    }

    public void addAccount(Account a) {
        this.accountDao.create(a);
        Customer c = a.getCustomer();
        this.custDao.refresh(c);
        //        boolean isThere = false;
        //        for (Account acc : a.getCustomer().getAccounts()) {
        //            if (acc.equals(a)) {
        //                isThere = true;
        //            }
        //        }
        //        if (!isThere) {
        //            Customer c = a.getCustomer();
        //            c.getAccounts().add(a);
        //            try {
        //                this.custDao.update(c);
        //            } catch (EntityAlreadyUpdatedException ex) {
        //                LOG.error("Error during customer update! Customer is already updated!");
        //            }
        //        }
    }

    public List<Account> getAllAccounts() {
        return this.accountDao.findAll();
    }

    public void updateAccount(Account a) {
        try {
            this.accountDao.update(a);
        } catch (EntityAlreadyUpdatedException ex) {
            LOG.warn("entity is already updated");
        }
    }

    public List<BankTransaction> getTransactionsForAccount(Account account) {
        return this.bankTransDao.getTransByAccount(account);
    }
}
