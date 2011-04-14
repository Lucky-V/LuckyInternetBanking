
package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
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
public class AccountBean {

    @EJB
    private CustomerSessionBean custBean;

    @ManagedProperty("#{param.customerId}")
    private Long customerId;

    public List<Account> getAccounts() {
        Customer cust = this.custBean.getCustomerById(this.customerId);
        return cust.getAccounts();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

}
