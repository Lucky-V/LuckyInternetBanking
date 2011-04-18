package cz.cvut.fel.vyhliluk.tjv.centralbank.converter;

import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Date: 18.4.2011
 * Time: 16:28:44
 * @author Lucky
 */
@XmlRootElement(name = "transfer")
public class TransferConverter {

    private Integer bankFrom;
    private Long accountFrom;
    private Integer bankTo;
    private Long accountTo;
    private BigDecimal amount;
    private String currency;
    private String description;
    private Date dateTime;

    public TransferConverter() {
    }

    public TransferConverter(
            Integer bankFrom, Long accountFrom, Integer bankTo, Long accountTo,
            BigDecimal amount, String currency, String description, Date dateTime) {
        this.bankFrom = bankFrom;
        this.accountFrom = accountFrom;
        this.bankTo = bankTo;
        this.accountTo = accountTo;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.dateTime = dateTime;
    }

    @XmlElement
    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    @XmlElement
    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    @XmlElement
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @XmlElement
    public Integer getBankFrom() {
        return bankFrom;
    }

    public void setBankFrom(Integer bankFrom) {
        this.bankFrom = bankFrom;
    }

    @XmlElement
    public Integer getBankTo() {
        return bankTo;
    }

    public void setBankTo(Integer bankTo) {
        this.bankTo = bankTo;
    }

    @XmlElement
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
