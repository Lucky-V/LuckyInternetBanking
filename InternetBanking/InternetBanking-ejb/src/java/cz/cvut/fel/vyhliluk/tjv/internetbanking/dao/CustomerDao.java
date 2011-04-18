package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class CustomerDao extends AbstractDao<Customer, Long> {

    public CustomerDao() {
        super(Customer.class);
    }

    public void invalidate(Long id) {
        Customer c = this.em.find(Customer.class, id);
        c.setValid(Boolean.FALSE);
        this.em.merge(c);
    }
}
