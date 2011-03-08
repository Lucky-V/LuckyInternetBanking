package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 * Date: 8.3.2011
 * Time: 12:51:30
 * @author Lucky
 */
@Entity
public class BankTransaction implements Serializable {
    @Id
    private Long id;

    @ManyToOne
    private Account accountFrom;

    @ManyToOne
    private Account accountTo;

    private BigDecimal amountFrom;

    private BigDecimal amountTo;

    private String description;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
