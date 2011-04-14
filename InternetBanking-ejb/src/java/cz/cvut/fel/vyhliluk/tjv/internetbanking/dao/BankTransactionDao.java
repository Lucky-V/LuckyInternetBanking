
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class BankTransactionDao extends AbstractDao<BankTransaction, Long> {

    public BankTransactionDao() {
        super(BankTransaction.class);
    }

    public List<BankTransaction> getAllTransactionsLimited(int from, int to) {
        Query q = this.em.createNamedQuery("BankTransaction.viewAll");
        q.setFirstResult(from);
        q.setMaxResults(to);
        return q.getResultList();
    }

    public List<BankTransaction> getTransByAccount(Account acc) {
        Query q = this.em.createNamedQuery("BankTransaction.getByAccount");
        q.setParameter("account", acc);
        return q.getResultList();
    }
    
    
 
}
