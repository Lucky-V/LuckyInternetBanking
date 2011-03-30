/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
public class BankTransactionSessionBean implements BankTransactionSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addTransaction(BankTransaction transaction) {
        this.em.persist(transaction);
    }

    @Override
    public List<BankTransaction> getAllTransactions() {
        Query q = this.em.createNamedQuery("BankTransaction.viewAll");
        return q.getResultList();
    }

    @Override
    public List<BankTransaction> getAllTransactionsLimited(int from, int to) {
        Query q = this.em.createNamedQuery("BankTransaction.viewAll");
        q.setFirstResult(from);
        q.setMaxResults(to);
        return q.getResultList();
    }
 
}
