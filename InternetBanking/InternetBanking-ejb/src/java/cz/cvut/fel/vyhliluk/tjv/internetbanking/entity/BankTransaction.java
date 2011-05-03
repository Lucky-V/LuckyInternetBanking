package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 * Date: 9.3.2011
 * Time: 11:34:52
 * @author Lucky
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "BankTransaction.findAll", query = "SELECT bt FROM BankTransaction bt ORDER BY bt.id DESC"),
    @NamedQuery(name = "BankTransaction.getByAccountId", query = "SELECT bt FROM BankTransaction bt where (bt.accountFrom = :account and bt.bankFrom IS NULL) or (bt.accountTo = :account and bt.bankTo IS NULL) ORDER BY bt.id DESC")
})
public class BankTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable=true)
    private Bank bankFrom;
    @ManyToOne
    @JoinColumn(nullable=true)
    private Bank bankTo;
    @Column(nullable = false)
    private Long accountFrom;
    @Column(nullable = false)
    private Long accountTo;
    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal amountFrom;
    @Column(precision = 20, scale = 2, nullable = false)
    private BigDecimal amountTo;
    private String description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateTime;

    public BankTransaction() {
    }

    public BankTransaction(Long accountFrom, Bank bankFrom, Long accountTo, Bank bankTo, BigDecimal amountFrom, BigDecimal amountTo, String description, Date dateTime) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.bankFrom = bankFrom;
        this.bankTo = bankTo;
        this.amountFrom = amountFrom;
        this.amountTo = amountTo;
        this.description = description;
        this.dateTime = dateTime;
    }

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

    public Bank getBankFrom() {
        return bankFrom;
    }

    public void setBankFrom(Bank bankFrom) {
        this.bankFrom = bankFrom;
    }

    public Bank getBankTo() {
        return bankTo;
    }

    public void setBankTo(Bank bankTo) {
        this.bankTo = bankTo;
    }

    public BigDecimal getAmountFrom() {
        return amountFrom;
    }

    public void setAmountFrom(BigDecimal amountFrom) {
        this.amountFrom = amountFrom;
    }

    public BigDecimal getAmountTo() {
        return amountTo;
    }

    public void setAmountTo(BigDecimal amountTo) {
        this.amountTo = amountTo;
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
        if (!(object instanceof BankTransaction)) {
            return false;
        }
        BankTransaction other = (BankTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction[id=" + id + "]";
    }
}
