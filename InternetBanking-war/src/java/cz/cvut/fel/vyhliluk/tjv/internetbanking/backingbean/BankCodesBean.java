package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Bank;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.BankSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Lucky
 */
@ManagedBean(name="bankCodes")
@RequestScoped
public class BankCodesBean {

    @EJB
    private BankSessionBean bankBean;

    @Pattern(regexp="^\\d\\d\\d$")
    private String bankCode;

    @Pattern(regexp="^.+$")
    private String bankName;

    @NotNull
    private Integer selectedBankId;

    public List<Bank> getAllBanks() {
        return this.bankBean.getAllBanks();
    }

    public String update() {
        Bank b = this.bankBean.getByCode(Integer.parseInt(bankCode));
        if (b == null) {
            b = new Bank();
            b.setCode(Integer.parseInt(bankCode));
        }
        b.setName(bankName);
        this.bankBean.updateBankCode(b);

        this.bankCode = "";
        this.bankName = "";

        BundleUtil.addOkMessage(
                "bank_codes_updated_ok_msg_title",
                "bank_codes_updated_ok_msg");
        return null;
    }

    public String delete() {
        this.bankBean.removeBank(this.selectedBankId);
        
        this.selectedBankId = null;

        BundleUtil.addOkMessage(
                "bank_codes_deleted_ok_msg_title",
                "bank_codes_deleted_ok_msg");
        return null;
    }

    public List<SelectItem> getBankSelectItems() {
        List<Bank> banks = this.getAllBanks();
        List<SelectItem> res = new ArrayList<SelectItem>(banks.size());
        for (Bank bank : banks) {
            res.add(new SelectItem(bank.getCode(), bank.getName()));
        }
        return res;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getSelectedBankId() {
        return selectedBankId;
    }

    public void setSelectedBankId(Integer selectedBankId) {
        this.selectedBankId = selectedBankId;
    }
    
}