package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.timer;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.AccountDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CurrencyDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CurrentCurrencyRateDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrentCurrencyRate;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.CurrencyUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
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
public class PayInterestSessionBean {

    private static final Logger LOG = LoggerFactory.getLogger(PayInterestSessionBean.class);

    @EJB
    private AccountDao accountBean;
    @EJB
    private BankTransactionDao bankTransBean;
    @EJB
    private CurrencyDao currencyBean;
    @EJB
    private CurrentCurrencyRateDao currentCurrencyRateBean;

    @Schedule(hour="0",minute="0",second="0")
    //@Schedule(hour="*",minute="*",second="*/12")
    public void payInterests() {
        LOG.info("Pay Interest Start");

        List<Account> accounts = this.accountBean.findAll();

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
            try {
                this.accountBean.update(account);
            } catch (EntityAlreadyUpdatedException ex) {
                LOG.error(ex.getMessage());
            }
        }

        this.setCurrentCurrencies();
        LOG.info("Pay Interest Done");
    }

    private void createInterestTransaction(Account acc, BigDecimal interest, Currency c) {
        BankTransaction bt = new BankTransaction();
        bt.setAccountFrom(acc);
        bt.setAccountTo(acc);
        bt.setAmountFrom(BigDecimal.ZERO);
        bt.setAmountTo(CurrencyUtil.setScale(interest, c));
        bt.setDateTime(new Date());
        bt.setDescription("interest");

        this.bankTransBean.create(bt);
    }

    private void setCurrentCurrencies() {
        for (Currency c : this.currencyBean.findAll()) {
            CurrencyRate rate = c.getRate();
            CurrentCurrencyRate currentRate = this.currentCurrencyRateBean.getCurrentRateByCurrencyCode(c.getCode());
            if (rate != null) {
                if (currentRate == null) {
                    currentRate = new CurrentCurrencyRate();
                    currentRate.setCurrency(c);
                }
                currentRate.setRate(rate.getRate());
                
                try {
                    this.currentCurrencyRateBean.update(currentRate);
                } catch (EntityAlreadyUpdatedException ex) {
                    LOG.error(ex.getMessage());
                }
            } else {
                if (currentRate != null) {
                    this.currentCurrencyRateBean.deleteById(currentRate.getId());
                }
            }
        }
    }
    
 
}
