package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Date: 9.3.2011
 * Time: 11:28:08
 * @author Lucky
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Currency.getAll", query="SELECT c FROM Currency c"),
    @NamedQuery(name="Currency.getByCode", query="SELECT c from Currency c where c.code=:code")
})
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length=3)
    private String code;

    private String name;

    private Integer decimalDigits;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(Integer decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Currency)) {
            return false;
        }
        Currency other = (Currency) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency[id=" + code + "]";
    }

}
