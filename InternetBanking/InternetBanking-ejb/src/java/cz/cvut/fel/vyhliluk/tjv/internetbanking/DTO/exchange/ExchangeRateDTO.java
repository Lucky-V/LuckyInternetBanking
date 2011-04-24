package cz.cvut.fel.vyhliluk.tjv.internetbanking.DTO.exchange;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Date: 18.4.2011
 * Time: 14:46:37
 * @author Lucky
 */
@XmlRootElement(name = "exchangeRate")
@XmlType(propOrder = {"currencyFrom", "currencyTo", "rate"})
public class ExchangeRateDTO {

    private String currencyFrom;
    private String currencyTo;
    private BigDecimal rate;

    public ExchangeRateDTO() {
    }

    public ExchangeRateDTO(String currencyFrom, String currencyTo, BigDecimal rate) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rate = rate;
    }

    @XmlElement
    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    @XmlElement
    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    @XmlElement
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
