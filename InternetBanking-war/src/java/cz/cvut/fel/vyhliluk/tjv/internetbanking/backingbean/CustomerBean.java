/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CustomerSessionBeanLocal;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.persistence.OptimisticLockException;
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
    private Long customerId;

    @NotNull
    private Integer version;

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
        
        this.customerId = c.getId();
        this.firstName = c.getFirstName();
        this.surname = c.getSurname();
        this.email = c.getEmail();
        this.customerId = c.getId();
        this.version = c.getVersion();
    }

    public void onRowUnselect(UnselectEvent event) {
        this.customerId = null;
        this.firstName = null;
        this.surname = null;
        this.email = null;
        this.customerId = null;
        this.version = null;
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
        Customer c = this.custSessionBean.getCustomerById(this.customerId);
        if (c == null) {
            BundleUtil.addErrMessage(
                    "update_customer_updated_err_noselect_msg_title",
                    "update_customer_updated_err_noselect_msg");

            return null;
        }

        c.setVersion(this.version);
        c.setFirstName(this.firstName);
        c.setSurname(this.surname);
        c.setEmail(this.email);

        try {
            this.custSessionBean.updateCustomer(c);
        } catch (EntityAlreadyUpdatedException ex) {
            BundleUtil.addErrMessage(
                "update_customer_updated_err_optimlock_title",
                "update_customer_updated_err_optimlock_msg");
            return null;
        }

        BundleUtil.addOkMessage(
                "update_customer_updated_ok_msg_title",
                "update_customer_updated_ok_msg");

        return null;


    }

    public String delete() {
        this.custSessionBean.invalidate(this.customerId);

        this.customerId = null;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
