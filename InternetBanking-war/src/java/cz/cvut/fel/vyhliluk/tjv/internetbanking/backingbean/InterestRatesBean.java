/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencyRateSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencySessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.math.BigDecimal;
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
@ManagedBean(name="interestRates")
@RequestScoped
public class InterestRatesBean {

    @EJB
    private CurrencyRateSessionBean rateBean;
    @EJB
    private CurrencySessionBean currencyBean;

    @Pattern(regexp="^\\w\\w\\w$")
    private String selectedCurrency;

    @Pattern(regexp="^\\+?\\d+(\\.\\d{0,2})?$")
    private String rate;

    @NotNull
    private Integer selectedRate;
    
    public String update() {
        CurrencyRate cr = this.rateBean.getByCurrencyCode(this.selectedCurrency);
        if (cr == null) {
            cr = new CurrencyRate();
            Currency c = this.currencyBean.getByCode(selectedCurrency);
            cr.setCurrency(c);
        }
        cr.setRate(new BigDecimal(rate));
        this.rateBean.update(cr);

        this.selectedCurrency = null;
        this.rate = null;

        BundleUtil.addOkMessage(
                "interest_rates_updated_ok_msg_title",
                "interest_rates_updated_ok_msg");
        
        return null;
    }

    public List<CurrencyRate> getAllRates() {
        return this.rateBean.getAllRates();
    }

    public List<SelectItem> getRateSelectItems() {
        List<CurrencyRate> rates = this.getAllRates();
        List<SelectItem> res = new ArrayList<SelectItem>(rates.size());
        for (CurrencyRate r : rates) {
            res.add(new SelectItem(r.getId(), r.getCurrency().getCode()));
        }
        return res;
    }

    public String delete() {
        this.rateBean.remove(this.selectedRate);

        this.selectedRate = null;

        BundleUtil.addOkMessage(
                "interest_rates_deleted_ok_msg_title",
                "interest_rates_deleted_ok_msg");

        return null;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }

    public Integer getSelectedRate() {
        return selectedRate;
    }

    public void setSelectedRate(Integer selectedRate) {
        this.selectedRate = selectedRate;
    }

}
