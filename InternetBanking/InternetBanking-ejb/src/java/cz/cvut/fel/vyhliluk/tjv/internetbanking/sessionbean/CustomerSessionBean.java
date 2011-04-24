
package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.DTO.exchange.ExchangeRateDTO;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.AccountDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CurrencyDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.CurrencyUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
@RolesAllowed("Customer")
public class CustomerSessionBean {
    
    private static final Logger LOG = LoggerFactory.getLogger(CustomerSessionBean.class);

    @EJB
    private AccountDao accountDao;

    @EJB
    private BankDao bankDao;

    @EJB
    private CurrencyDao currencyDao;

    @EJB
    private BankTransactionDao bankTransDao;

    public List<BankTransaction> getTransactions(Long accountId, Customer loggedCustomer) {
        Account acc = this.accountDao.findById(accountId);
        this.verifyAccount(loggedCustomer, acc);

        return this.bankTransDao.getTransByAccount(acc);
    }

    public void transferMoney(
            Customer loggedCustomer, Long accountFrom, Long accountTo,
            Integer bankTo, BigDecimal amount, String currency, String desc) {
        try {
            Account accFrom = this.accountDao.findById(accountFrom);
            this.verifyAccount(loggedCustomer, accFrom);
            Currency curr = this.currencyDao.findById(currency);
            Currency currFromAcc = accFrom.getCurrency();
            BigDecimal rate = this.getCurrenciesRate(curr, currFromAcc);
            BigDecimal amountFrom = CurrencyUtil.setCountScale(amount.multiply(rate));
            BigDecimal accFromBalance = CurrencyUtil.setCountScale(accFrom.getBalance().subtract(amountFrom));
            accFrom.setBalance(accFromBalance);
            
            if (bankTo == null) {
                //Inbank transaction
                Account accTo = this.accountDao.findById(accountTo);
                Currency currToAcc = accTo.getCurrency();
                rate = this.getCurrenciesRate(curr, currToAcc);
                BigDecimal amountTo = CurrencyUtil.setCountScale(amount.multiply(rate));
                BigDecimal accToBalance = CurrencyUtil.setCountScale(accTo.getBalance().add(amountTo));
                accTo.setBalance(accToBalance);
                BankTransaction bt = new BankTransaction(accFrom, accTo, amountFrom, amountTo, desc, new Date());

                this.accountDao.update(accFrom);
                this.accountDao.update(accTo);
                this.bankTransDao.create(bt);
            } else {
                //Interbank transaction
                LOG.error("Not implemented yet!");
            }
        } catch (EntityAlreadyUpdatedException ex) {
            LOG.warn("Error: "+ ex.getMessage());
        }
    }

    private void verifyAccount(Customer cust, Account acc) {
        if (!cust.equals(acc.getCustomer())) {
            throw new SecurityException("Not your account!!!");
        }
    }

    private BigDecimal getCurrenciesRate(Currency c1, Currency c2) {
        try {
            String name = c1.getCode() + c2.getCode();
            URL url = new URL("http://localhost:8080/ExchangeOffice/resources/exchangerate/"+ name);
            URLConnection conn = url.openConnection();
            JAXBContext ctx = JAXBContext.newInstance(ExchangeRateDTO.class);
            Unmarshaller unm = ctx.createUnmarshaller();
            ExchangeRateDTO dto = (ExchangeRateDTO) unm.unmarshal(conn.getInputStream());
            return dto.getRate();
        } catch (Exception ex) {
            LOG.error("Error: "+ ex.getMessage());
        }
        return null;
    }
 
}
