
package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.customer;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.LoginBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Lucky
 */
@ManagedBean
@RequestScoped
public class CustomerAccountBean {

    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;

    public List<Account> getAccounts() {
        Customer c = this.loginBean.getCustomer();
        return c.getAccounts();
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}
