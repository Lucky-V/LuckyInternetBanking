package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean.manager;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.manager.CustomerManagementSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.manager.UserSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean
@RequestScoped
public class CustomerManagerBean {

    @EJB
    private CustomerManagementSessionBean custBean;
    @EJB
    private UserSessionBean userBean;

    @Length(min=1, max=40)
    private String firstName;

    @Length(min=1, max=40)
    private String surname;

    @Pattern(regexp="^[\\w\\.=-]+@[\\w\\.-]+\\.cz$")
    private String email;

    @Length(min=5, max=20)
    private String password;

    private String password2;

    @NotNull
    private Long customerId;

    @NotNull
    private Integer version;

    public List<Customer> getAllCustomers() {
        return this.custBean.getAllCustomers();
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
        if (!password.equals(password2)) {
            FacesContext.getCurrentInstance().addMessage(
                    "customerForm:password2",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", BundleUtil.getString("add_delete_customer_validation_password2")));
            return null;
        }

        String username = this.createUserName();

        Customer c = new Customer();
        c.setFirstName(firstName);
        c.setSurname(surname);
        c.setEmail(email);
        c.setPassword(password);
        c.setUsername(username);
        this.custBean.addCustomer(c);

        this.firstName = "";
        this.surname = "";
        this.email = "";

        BundleUtil.addOkMessage(
                "add_delete_customer_added_ok_msg_title",
                "add_delete_customer_added_ok_msg");

        return null;
    }

    public String update() {
        Customer c = this.custBean.getCustomerById(this.customerId);
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
            this.custBean.updateCustomer(c);
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
        this.custBean.invalidate(this.customerId);

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }


    //================== PRIVATE METHODS =======================================

    private String createUserName() {
        String usernameTemplate = surname.substring(0, surname.length() > 5 ? 5 : surname.length())
                + firstName.substring(0, firstName.length() > 3 ? 3 : firstName.length());
        usernameTemplate = Normalizer.normalize(usernameTemplate, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]","");
        String username = usernameTemplate;
        Integer n = 0;
        while (! this.userBean.isUsernameFree(username)) {
            n++;
            username = usernameTemplate + n;
        }
        
        return username;
    }

}
