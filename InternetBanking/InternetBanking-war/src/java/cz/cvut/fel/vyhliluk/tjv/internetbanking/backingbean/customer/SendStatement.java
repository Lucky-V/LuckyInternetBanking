package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.customer;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.LoginBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CustomerSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.NotificationSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
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
public class SendStatement {

    @EJB
    private CustomerSessionBean custBean;

    @EJB
    private NotificationSessionBean notifBean;

    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;

    public Long accountId;

    public String sendStatement() {
        Customer c = this.loginBean.getCustomer();
        List<BankTransaction> trans = this.custBean.getTransactions(accountId, c);

        StringBuilder sb = new StringBuilder();
        sb.append("Dobrý den, \nVy budete ");
        sb.append(c.getFirstName()).append(" ").append(c.getSurname()).append(".");
        sb.append("\n\nzasíláme Vám Váš výpis transakcí.\n\n\n");

        this.notifBean.sendMessage(c, "Výpis", sb.toString());

        BundleUtil.addOkMessage(
                "customer_send_statement_ok_title",
                "customer_send_statement_ok_msg");

        return null;
    }

    //==============GETTERS SETTERS=============================================

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
