package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.DTO.exchange.ExchangeRateDTO;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.AccountDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankTransactionDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CurrencyDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Account;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Bank;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.TransferMoneyException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.UnknownCurrencyRateException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.CurrencyUtil;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.ws.centralbank.CentralBankWS;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.ws.centralbank.CentralBankWSService;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.ws.centralbank.ResponseDTO;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.ws.centralbank.TransferDTO;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
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
            Integer bankTo, BigDecimal amount, String currency, String desc) throws TransferMoneyException {
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
                BankTransaction bt = new BankTransaction(
                        accFrom.getId(), null,
                        accTo.getId(), null,
                        amountFrom, amountTo, desc, new Date());

                this.bankTransDao.create(bt);
                this.accountDao.update(accFrom);
                this.accountDao.update(accTo);
            } else {
                //Interbank transaction
                Date d = new Date();
                Bank bank = this.bankDao.findById(bankTo);
                BankTransaction bt = new BankTransaction(
                        accFrom.getId(), null,
                        accountTo, bank,
                        amountFrom, amount, desc, d);

                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(d);
                TransferDTO dto = new TransferDTO();
                dto.setAccountFrom(accFrom.getId());
                dto.setAccountTo(accountTo);
                dto.setAmount(amount);
                dto.setBankFrom(000);
                dto.setBankTo(bankTo);
                dto.setCurrency(currency);
                dto.setDateTime(new XMLGregorianCalendarImpl(cal));
                dto.setDescription(desc);
                CentralBankWS ws = new CentralBankWSService().getCentralBankWSPort();
                ResponseDTO response = ws.addTransfer(dto);
                if (response.getCode() != 0) {
                    LOG.error("Nepodarilo se vlozit zaznam do central banky");
                    throw new TransferMoneyException("Unable to add transfer into central bank db", null);
                } else {
                    this.bankTransDao.create(bt);
                    this.accountDao.update(accFrom);
                }
            }
        } catch (UnknownCurrencyRateException ex) {
            LOG.error("Unknown currency: "+ ex.getMessage());
            throw new TransferMoneyException("Error during rate gathering", ex);
        } catch (EntityAlreadyUpdatedException ex) {
            LOG.warn("Error: " + ex.getMessage());
        }
    }

    private void verifyAccount(Customer cust, Account acc) {
        if (!cust.equals(acc.getCustomer())) {
            throw new SecurityException("Not your account!!!");
        }
    }

    private BigDecimal getCurrenciesRate(Currency c1, Currency c2) throws UnknownCurrencyRateException {
        try {
            String name = c1.getCode() + c2.getCode();
            URL url = new URL("http://localhost:8080/ExchangeOffice/resources/exchangerate/" + name);
            URLConnection conn = url.openConnection();
            JAXBContext ctx = JAXBContext.newInstance(ExchangeRateDTO.class);
            Unmarshaller unm = ctx.createUnmarshaller();
            ExchangeRateDTO dto = (ExchangeRateDTO) unm.unmarshal(conn.getInputStream());
            LOG.info("Rate (" + name + ") = " + dto.getRate());
            if (dto.getCurrencyFrom() == null) {
                throw new UnknownCurrencyRateException("Některá měna nabyla v exchangeoffice nalezena!");
            }
            return dto.getRate();
        } catch (Exception ex) {
            LOG.error("Error: " + ex.getMessage());
            throw new UnknownCurrencyRateException(ex.getMessage());
        }
    }
}
