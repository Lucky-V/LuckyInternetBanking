/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CustomerSessionBeanLocal;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Lucky
 */
@ManagedBean(name="customer")
@RequestScoped
public class CustomerBean {

    @EJB
    private CustomerSessionBeanLocal custSessionBean;

    @Length(min=1, max=40)
    private String firstName;

    @Length(min=1, max=40)
    private String surname;

    @Pattern(regexp="^[\\w\\.=-]+@[\\w\\.-]+\\.cz$")
    private String email;

    @NotNull
    private Long selectedCustomerId;

    @NotNull
    private Customer selectedCustomer;

    public List<Customer> getAllCustomers() {
        return this.custSessionBean.getAllCustomers();
    }

    public List<SelectItem> getCustomerSelectItems() {
        List<Customer> custs = this.getAllCustomers();
        List<SelectItem> res = new ArrayList<SelectItem>(custs.size());
        for (Customer c : custs) {
            res.add(new SelectItem(c.getId(), c.getSurname() +", "+ c.getFirstName()));
        }
        return res;
    }

    public void onRowSelect(SelectEvent event) {
        Customer c = (Customer) event.getObject();
        
        this.selectedCustomerId = c.getId();
        this.firstName = c.getFirstName();
        this.surname = c.getSurname();
        this.email = c.getEmail();
    }

    public void onRowUnselect(UnselectEvent event) {
        this.selectedCustomerId = null;
        this.firstName = null;
        this.surname = null;
        this.email = null;
    }

    public String createCustomer() {
        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setSurname(surname);
        c.setEmail(email);
        this.custSessionBean.addCustomer(c);

        this.firstName = "";
        this.surname = "";
        this.email = "";

        BundleUtil.addOkMessage(
                "add_delete_customer_added_ok_msg_title",
                "add_delete_customer_added_ok_msg");

        return null;
    }

    public String update() {
        if (this.selectedCustomer == null) {
            BundleUtil.addErrMessage(
                    "update_customer_updated_err_noselect_msg_title",
                    "update_customer_updated_err_noselect_msg");
            return null;
        }

        this.selectedCustomer.setFirstName(firstName);
        this.selectedCustomer.setSurname(surname);
        this.selectedCustomer.setEmail(email);
        this.custSessionBean.updateCustomer(this.selectedCustomer);

        BundleUtil.addOkMessage(
                "update_customer_updated_ok_msg_title",
                "update_customer_updated_ok_msg");

        return null;


    }

    public String delete() {
        this.custSessionBean.invalidate(this.selectedCustomerId);

        this.selectedCustomerId = null;

        BundleUtil.addOkMessage(
                "add_delete_customer_deleted_ok_msg_title",
                "add_delete_customer_deleted_ok_msg");

        return null;
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

    public Long getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public void setSelectedCustomerId(Long selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

}
