package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.timer;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrentCurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.AccountSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.BankTransactionSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencyRateSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CurrencySessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.CurrencyUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lucky
 */
@Singleton
@Startup
@RunAs("Manager")
public class PayInterestSessionBean {

    private static final Logger LOG = LoggerFactory.getLogger(PayInterestSessionBean.class);
    @EJB
    private AccountSessionBean accountBean;
    @EJB
    private BankTransactionSessionBean bankTransBean;
    @EJB
    private CurrencySessionBean currencyBean;
    @EJB
    private CurrencyRateSessionBean currentCurrencyRateBean;

    @Schedule(hour="0",minute="0",second="0")
    //@Schedule(hour = "*", minute = "*", second = "*/15")
    public void payInterests() {
        LOG.info("Pay Interest Start ===>");

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
            BigDecimal minBalance = CurrencyUtil.setCountScale(this.getMinimalBalance(account));
            LOG.info("Min Balance (accId: " + account.getId() + "): " + minBalance);
            BigDecimal interest = minBalance.multiply(rateValue).divide(new BigDecimal(365), 4, RoundingMode.HALF_UP);
            accBalance = CurrencyUtil.setScale(accBalance.add(interest), currency);

            this.createInterestTransaction(account, interest, currency);

            account.setBalance(accBalance);
            this.accountBean.updateAccount(account);
        }

        this.setCurrentCurrencies();
        LOG.info("Pay Interest Done <===");
    }

    private void createInterestTransaction(Account acc, BigDecimal interest, Currency c) {
        BankTransaction bt = new BankTransaction();
        bt.setAccountFrom(acc.getId());
        bt.setAccountTo(acc.getId());
        bt.setBankFrom(null);
        bt.setBankTo(null);
        bt.setAmountFrom(BigDecimal.ZERO);
        bt.setAmountTo(CurrencyUtil.setScale(interest, c));
        bt.setDateTime(new Date());
        bt.setDescription("interest");

        this.bankTransBean.addTransaction(bt);
    }

    private void setCurrentCurrencies() {
        for (Currency c : this.currencyBean.getAllCurencies()) {
            CurrencyRate rate = c.getRate();
            CurrentCurrencyRate currentRate = this.currentCurrencyRateBean.getCurrentRateByCurrencyCode(c.getCode());
            if (rate != null) {
                if (currentRate == null) {
                    currentRate = new CurrentCurrencyRate();
                    currentRate.setCurrency(c);
                }
                currentRate.setRate(rate.getRate());

                this.currentCurrencyRateBean.updateCurrentCurrency(currentRate);
                this.currencyBean.updateCurrency(c);
            } else {
                if (currentRate != null) {
                    this.currentCurrencyRateBean.removeCurrentCurrencyRate(currentRate.getId());
                    this.currencyBean.updateCurrency(c);
                }
            }
        }
    }

    private BigDecimal getMinimalBalance(Account acc) {
        Date act = new Date();
        Date from = new Date(act.getYear(), act.getMonth(), act.getDate(), 0, 0, 0);
        Date to = new Date(act.getYear(), act.getMonth(), act.getDate(), 23, 59, 59);
        List<BankTransaction> bt = this.bankTransBean.getTransByAccountAndInterval(acc, from, to);
        BigDecimal balance = CurrencyUtil.setCountScale(acc.getBalance());
        BigDecimal minBalance = balance;
        for (BankTransaction trans : bt) {
            if (trans.getAccountTo().equals(acc.getId())) {
                balance = balance.subtract(trans.getAmountTo());
            } else {
                balance = balance.add(trans.getAmountFrom());
            }
            minBalance = balance.min(minBalance);
        }
        return minBalance;
    }
}
