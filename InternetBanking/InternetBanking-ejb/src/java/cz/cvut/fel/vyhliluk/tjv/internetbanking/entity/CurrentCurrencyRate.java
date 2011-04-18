package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Date: 9.3.2011
 * Time: 11:31:08
 * @author Lucky
 */
@Entity
@NamedQueries({
    @NamedQuery(name="CurrentCurrencyRate.findAll", query="SELECT cr FROM CurrentCurrencyRate cr"),
    @NamedQuery(name="CurrentCurrencyRate.getByCurrencyCode", query="SELECT cr FROM CurrentCurrencyRate cr where cr.currency.code=:code")
})
public class CurrentCurrencyRate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(unique=true)
    private Currency currency;

    @Column(precision=10, scale=2, nullable=false)
    private BigDecimal rate;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CurrentCurrencyRate)) {
            return false;
        }
        CurrentCurrencyRate other = (CurrentCurrencyRate) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.CurrentCurrencyRate[id=" + id + "]";
    }

}
