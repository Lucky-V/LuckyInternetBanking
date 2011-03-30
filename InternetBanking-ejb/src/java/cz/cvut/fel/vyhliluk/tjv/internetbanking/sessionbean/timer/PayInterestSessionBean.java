package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.timer;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrentCurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.AccountSessionBeanLocal;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.BankTransactionSessionBeanLocal;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencyRateSessionBeanLocal;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencySessionBeanLocal;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.CurrencyUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Lucky
 */
@Singleton
@Startup
public class PayInterestSessionBean {

    private static final Logger LOG = Logger.getLogger(PayInterestSessionBean.class.getName());

    @EJB
    private AccountSessionBeanLocal accountBean;
    @EJB
    private BankTransactionSessionBeanLocal bankTransBean;
    @EJB
    private CurrencySessionBeanLocal currencyBean;
    @EJB
    private CurrencyRateSessionBeanLocal currencyRateBean;

    @Schedule(hour="12",minute="0",second="0")
    public void payInterests() {
        System.out.println("xxx");
        LOG.info("Pay Interest Start");

        List<Account> accounts = this.accountBean.getAllAccounts();

        for (Account account : accounts) {
            Currency currency = account.getCurrency();
            CurrentCurrencyRate rate = currency.getCurrentRate();
            BigDecimal rateValue;

            if (rate != null) {
                rateValue = rate.getRate();
            } else {
                rateValue = BigDecimal.ZERO;
            }

            BigDecimal accBalance = CurrencyUtil.setCountScale(account.getBalance());
            BigDecimal interest = accBalance.multiply(rateValue).divide(new BigDecimal(365), 4, RoundingMode.HALF_UP);
            accBalance = CurrencyUtil.setScale(accBalance.add(interest), currency);

            this.createInterestTransaction(account, interest, currency);

            account.setBalance(accBalance);
            this.accountBean.updateAccount(account);
        }

        this.setCurrentCurrencies();
        LOG.info("Pay Interest Stop - OK");
    }

    private void createInterestTransaction(Account acc, BigDecimal interest, Currency c) {
        BankTransaction bt = new BankTransaction();
        bt.setAccountFrom(acc);
        bt.setAccountTo(acc);
        bt.setAmountFrom(BigDecimal.ZERO);
        bt.setAmountTo(CurrencyUtil.setScale(interest, c));
        bt.setDateTime(new Date());
        bt.setDescription("interest");

        this.bankTransBean.addTransaction(bt);
    }

    private void setCurrentCurrencies() {
        for (Currency c : this.currencyBean.getAllCurencies()) {
            CurrencyRate rate = c.getRate();
            CurrentCurrencyRate currentRate = this.currencyRateBean.getCurrentRateByCurrencyCode(c.getCode());
            if (rate != null) {
                if (currentRate == null) {
                    currentRate = new CurrentCurrencyRate();
                    currentRate.setCurrency(c);
                }
                currentRate.setRate(rate.getRate());
                this.currencyRateBean.updateCurrentCurrency(currentRate);
            } else {
                if (currentRate != null) {
                    this.currencyRateBean.removeCurrentCurrencyRate(currentRate.getId());
                }
            }
        }
    }
    
 
}
