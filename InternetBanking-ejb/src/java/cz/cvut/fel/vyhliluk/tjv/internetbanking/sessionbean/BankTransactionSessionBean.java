/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
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
 
}
