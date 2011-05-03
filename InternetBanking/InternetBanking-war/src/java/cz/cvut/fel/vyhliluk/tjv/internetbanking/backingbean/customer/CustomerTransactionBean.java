
package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.customer;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.LoginBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CustomerSessionBean;
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
public class CustomerTransactionBean {

    @EJB
    private CustomerSessionBean custBean;

    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;

    @ManagedProperty("#{param.accountId}")
    private Long accountId;

    public List<BankTransaction> getAccountTransactions() {
        if (this.accountId == null) {
            return null;
        }
        Customer c = this.loginBean.getCustomer();
        return this.custBean.getTransactions(accountId, c);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

}
