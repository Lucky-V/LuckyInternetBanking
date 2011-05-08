package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import java.util.Date;
import java.util.List;
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
public class BankTransactionSessionBean {

    @EJB
    private BankTransactionDao btDao;

    public void addTransaction(BankTransaction transaction) {
        this.btDao.create(transaction);
    }

    public List<BankTransaction> getAllTransactions() {
        return this.btDao.findAll();
    }

    public List<BankTransaction> getAllTransactionsLimited(int from, int to) {
        return this.btDao.getAllTransactionsLimited(from, to);
    }

    public List<BankTransaction> getTransByAccountAndInterval(Account acc, Date from, Date to) {
        return this.btDao.getTransByAccountAndInterval(acc, from, to);
    }
 
}
