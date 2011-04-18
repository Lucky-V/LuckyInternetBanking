/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.manager;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencySessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Lucky
 */
@ManagedBean(name="currencyCodes")
@RequestScoped
public class CurrencyCodesBean {

    @EJB
    private CurrencySessionBean currencyBean;

    @Pattern(regexp="^\\w\\w\\w$")
    private String code;

    @Pattern(regexp="^.+$")
    private String name;

    @DecimalMax(value="2")
    @DecimalMin(value="0")
    private Integer decimalDigits;

    @Pattern(regexp="^\\w\\w\\w$")
    private String selectedCurrency;

    public String update() {
        Currency c = this.currencyBean.getByCode(code);
        if (c == null) {
            c = new Currency();
            c.setCode(code);
        }
        c.setName(name);
        c.setDecimalDigits(decimalDigits);
        this.currencyBean.updateCurrency(c);

        this.code = "";
        this.name = "";
        this.decimalDigits = 0;

        BundleUtil.addOkMessage(
                "currency_codes_updated_ok_msg_title",
                "currency_codes_updated_ok_msg");

        return null;
    }

    public String delete() {
        this.currencyBean.deleteCurrency(this.selectedCurrency);

        this.selectedCurrency = null;

        BundleUtil.addOkMessage(
                "currency_codes_delete_ok_msg_title",
                "currency_codes_delete_ok_msg");

        return null;
    }

    public List<Currency> getAllCurrencies() {
        return this.currencyBean.getAllCurencies();
    }

    public List<Currency> getCurrenciesWithRate() {
        return this.currencyBean.getCurrenciesWithRate();
    }

    public List<SelectItem> getCurrencyItems() {
        List<Currency> currencies = this.getAllCurrencies();
        List<SelectItem> res = new ArrayList<SelectItem>(currencies.size());
        for (Currency currency : currencies) {
            res.add(new SelectItem(currency.getCode(), currency.getName()));
        }
        return res;
    }

    public List<SelectItem> getCurrencyItemsWithRate() {
        List<Currency> currencies = this.getCurrenciesWithRate();
        List<SelectItem> res = new ArrayList<SelectItem>(currencies.size());
        for (Currency currency : currencies) {
            res.add(new SelectItem(currency.getCode(), currency.getName()));
        }
        return res;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

}
