package cz.cvut.fel.vyhliluk.tjv.exchoffice.converter;

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
@XmlType
public class ExchangeRateConverter {

    private String currency;
    private BigDecimal rate;

    public ExchangeRateConverter() {
    }

    public ExchangeRateConverter(String currency, BigDecimal rate) {
        this.currency = currency;
        this.rate = rate;
    }

    @XmlElement(name="currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @XmlElement(name="rate")
    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
