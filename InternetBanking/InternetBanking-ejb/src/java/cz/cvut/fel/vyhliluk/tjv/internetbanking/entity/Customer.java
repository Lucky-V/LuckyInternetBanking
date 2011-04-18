package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.UserRole;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 * Date: 9.3.2011
 * Time: 11:32:15
 * @author Lucky
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c where c.valid='true'")
})
@DiscriminatorValue(value=UserRole.CUSTOMER)
public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    @Column(nullable=false)
    private String firstName;

    @Column(nullable=false)
    private String surname;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private Boolean valid = true;

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer[id=" + id + "]";
    }

}
