package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.CustomerDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class CustomerSessionBean {

    @EJB
    private CustomerDao custDao;

    public void addCustomer(Customer c) {
        this.custDao.create(c);
    }

    public void updateCustomer(Customer c) throws EntityAlreadyUpdatedException {
        this.custDao.update(c);
    }

    public List<Customer> getAllCustomers() {
        return this.custDao.findAll();
    }

    public void invalidate(Long id) {
        this.custDao.invalidate(id);
    }

    public Customer getCustomerById(Long id) {
        return this.custDao.findById(id);
    }
}
