package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Date: 8.3.2011
 * Time: 12:46:39
 * @author Lucky
 */
@Entity
public class Account implements Serializable {
    @Id
    private Long id;

    @ManyToOne
    private Currency currency;

    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
