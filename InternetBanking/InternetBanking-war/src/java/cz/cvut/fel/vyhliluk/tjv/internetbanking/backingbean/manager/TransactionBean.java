package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.manager;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.AccountSessionBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Lucky
 */
@ManagedBean
@RequestScoped
public class TransactionBean {

    @EJB
    private AccountSessionBean accBean;
    @ManagedProperty("#{param.accountId}")
    private Long accountId;

    public List<BankTransaction> getAccountTransactions() {
        if (accountId == null) {
            return null;
        }
        Account acc = this.accBean.getById(this.accountId);
        return this.accBean.getTransactionsForAccount(acc);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
