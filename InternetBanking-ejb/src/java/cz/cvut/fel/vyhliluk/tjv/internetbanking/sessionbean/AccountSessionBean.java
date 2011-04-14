package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.AccountDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class AccountSessionBean {

    @EJB
    private AccountDao accountDao;

    @EJB
    private BankTransactionDao bankTransDao;

    public Account getById(Long id) {
        return this.accountDao.findById(id);
    }

    public void addAccount(Account a) {
        this.accountDao.create(a);
    }

    public List<Account> getAllAccounts() {
        return this.accountDao.findAll();
    }

    public void updateAccount(Account a) {
        try {
            this.accountDao.update(a);
        } catch (EntityAlreadyUpdatedException ex) {
            Logger.getLogger(AccountSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<BankTransaction> getTransactionsForAccount(Account account) {
        return this.bankTransDao.getTransByAccount(account);
    }
 
}
