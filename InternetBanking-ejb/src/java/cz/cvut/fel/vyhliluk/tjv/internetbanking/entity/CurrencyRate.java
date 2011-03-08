package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Date: 8.3.2011
 * Time: 12:03:56
 * @author Lucky
 */
@Entity
public class CurrencyRate implements Serializable {
    @Id
    private Long code;

    private Double rate;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}
