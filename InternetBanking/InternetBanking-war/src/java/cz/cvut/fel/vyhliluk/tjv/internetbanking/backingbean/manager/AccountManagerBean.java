/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.manager;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.AccountSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencySessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CustomerManagementSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lucky
 */
@ManagedBean
@RequestScoped
public class AccountManagerBean {

    @EJB
    private AccountSessionBean accountBean;
    @EJB
    private CustomerManagementSessionBean customerBean;
    @EJB
    private CurrencySessionBean currencyBean;

    @NotNull
    private Long selectedCustomerId;

    @NotNull
    private String selectedCurrencyCode;

    @DecimalMin(value="0")
    @Digits(integer=20,fraction=2)
    @NotNull
    private BigDecimal initBalance;
    
    public List<Account> getAllAccounts() {
        return this.accountBean.getAllAccounts();
    }

    public String add() {
        Customer cust = this.customerBean.getCustomerById(this.selectedCustomerId);
        Currency cur = this.currencyBean.getByCode(this.selectedCurrencyCode);
        BigDecimal balance = this.initBalance.setScale(cur.getDecimalDigits(), RoundingMode.HALF_UP);

        Account acc = new Account();
        acc.setCustomer(cust);
        acc.setCurrency(cur);
        acc.setBalance(balance);
        
        this.accountBean.addAccount(acc);

        this.selectedCurrencyCode = null;
        this.selectedCustomerId = null;
        this.initBalance = null;

        BundleUtil.addOkMessage(
                "add_account_ok_message_title",
                "add_account_ok_message");

        return null;
    }

    public BigDecimal getInitBalance() {
        return initBalance;
    }

    public void setInitBalance(BigDecimal initBalance) {
        this.initBalance = initBalance;
    }

    public String getSelectedCurrencyCode() {
        return selectedCurrencyCode;
    }

    public void setSelectedCurrencyCode(String selectedCurrencyCode) {
        this.selectedCurrencyCode = selectedCurrencyCode;
    }

    public Long getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public void setSelectedCustomerId(Long selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }

}