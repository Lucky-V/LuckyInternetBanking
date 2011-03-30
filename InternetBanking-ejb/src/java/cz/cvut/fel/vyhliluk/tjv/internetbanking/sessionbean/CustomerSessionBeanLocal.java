/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lucky
 */
@Local
public interface CustomerSessionBeanLocal {

    Customer getCustomerById(Long id);

    void addCustomer(Customer c);

    void updateCustomer(Customer c) throws EntityAlreadyUpdatedException;

    List<Customer> getAllCustomers();

    void invalidate(Long id);
}
