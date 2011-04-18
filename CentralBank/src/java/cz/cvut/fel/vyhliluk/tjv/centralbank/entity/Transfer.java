package cz.cvut.fel.vyhliluk.tjv.centralbank.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Date: 18.4.2011
 * Time: 15:31:20
 * @author Lucky
 */
@Entity
public class Transfer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private Integer bankFrom;

    @Column(nullable=false)
    private Long accountFrom;

    @Column(nullable=false)
    private Integer bankTo;

    @Column(nullable=false)
    private Long accountTo;

    @Column(scale=2, precision=20, nullable=false)
    private BigDecimal amount;

    @Column(length=3, nullable=false)
    private String currency;

    private String description;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getBankFrom() {
        return bankFrom;
    }

    public void setBankFrom(Integer bankFrom) {
        this.bankFrom = bankFrom;
    }

    public Integer getBankTo() {
        return bankTo;
    }

    public void setBankTo(Integer bankTo) {
        this.bankTo = bankTo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(object instanceof Transfer)) {
            return false;
        }
        Transfer other = (Transfer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fel.vyhliluk.tjv.centralbank.entity.Transfer[id=" + id + "]";
    }

}
