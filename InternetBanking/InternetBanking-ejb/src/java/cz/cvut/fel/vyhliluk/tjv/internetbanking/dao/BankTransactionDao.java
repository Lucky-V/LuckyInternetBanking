
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.CurrencyUtil;
import java.math.BigDecimal;
import java.util.Date;
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
        Query q = this.em.createNamedQuery("BankTransaction.getByAccountId");
        q.setParameter("account", acc.getId());
        return q.getResultList();
    }

    @Override
    public void create(BankTransaction entity) {
        BigDecimal from = CurrencyUtil.setDbScale(entity.getAmountFrom());
        BigDecimal to = CurrencyUtil.setDbScale(entity.getAmountTo());
        entity.setAmountFrom(from);
        entity.setAmountTo(to);
        super.create(entity);
    }

    public List<BankTransaction> getTransByAccountAndInterval(Account acc, Date from, Date to) {
        Query q = this.em.createNamedQuery("BankTransaction.getByAccountIdAndInterval");
        q.setParameter("account", acc.getId());
        q.setParameter("from", from);
        q.setParameter("to", to);
        return q.getResultList();
    }


    
    
 
}
