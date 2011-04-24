package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.customer;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.LoginBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CustomerSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Lucky
 */
@ManagedBean
@RequestScoped
public class TransferMoney {

    @EJB
    private CustomerSessionBean custBean;

    @ManagedProperty("#{loginBean}")
    private LoginBean loginBean;

    @NotNull
    private Long accountFromId;
    private Integer bankToId;
    @NotNull
    private Long accountToId;
    @DecimalMin(value = "0")
    @Digits(integer = 20, fraction = 2)
    @NotNull
    private BigDecimal amount;
    @NotNull
    @Length(min = 3, max = 3)
    private String currencyCode;
    private String description;

    public String transfer() {
        if (bankToId == null && accountFromId.equals(accountToId)) {
            BundleUtil.addErrMessage(
                    "customer_transfer_money_err_same_acc_title",
                    "customer_transfer_money_err_same_acc_title");
            return null;
        }

        this.custBean.transferMoney(
                loginBean.getCustomer(), accountFromId, accountToId, bankToId,
                amount, currencyCode, description);

        BundleUtil.addOkMessage(
                "customer_transfer_money_ok_title",
                "customer_transfer_money_ok_msg");

        return null;
    }

    public Long getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Long accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Long getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Long accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getBankToId() {
        return bankToId;
    }

    public void setBankToId(Integer bankToId) {
        this.bankToId = bankToId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CustomerSessionBean getCustBean() {
        return custBean;
    }

    public void setCustBean(CustomerSessionBean custBean) {
        this.custBean = custBean;
    }

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }
}
