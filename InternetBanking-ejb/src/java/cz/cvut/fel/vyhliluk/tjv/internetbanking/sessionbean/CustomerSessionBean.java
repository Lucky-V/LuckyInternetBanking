/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addCustomer(Customer c) {
        this.em.persist(c);
    }

    @Override
    public void updateCustomer(Customer c) {
        this.em.merge(c);
    }

    @Override
    public List<Customer> getAllCustomers() {
        Query q = this.em.createNamedQuery("Customer.findAll");
        return q.getResultList();
    }

    @Override
    public void invalidate(Long id) {
        Customer c = this.em.find(Customer.class, id);
        c.setValid(Boolean.FALSE);
        this.em.merge(c);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return this.em.find(Customer.class, id);
    }
}
