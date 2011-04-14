
package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Customer;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Manager;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.LoginBeanException;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.CustomerSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.UserSessionBean;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.BundleUtil;
import java.security.Principal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Lucky
 */
@ManagedBean
@RequestScoped
public class LoginBean {

    @EJB
    private UserSessionBean userBean;

    private String username;

    private String password;

    public String login() {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            req.login(username, password);
            return "index";
        } catch (ServletException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "",
                    BundleUtil.getString("login_error")));
            return null;
        }
        
    }

    public String logout() {
        try {
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            req.logout();
            return "index";
        } catch (ServletException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "",
                    BundleUtil.getString("login_error")));
            return null;
        }
    }

    public String getUser() {
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (p != null) {
            return p.getName();
        }
        return null;
    }

    public boolean isLogged() {
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return p != null;
    }

    public boolean isCustomer() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Customer");
    }

    public boolean isManager() {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Manager");
    }

    /**
     * Returns the Customer entity related to the logged user
     * @return
     */
    public Customer getCustomer() {
        if (!this.isLogged()) {
            throw new LoginBeanException("User is not logged!");
        }
        if (!this.isCustomer()) {
            throw new LoginBeanException("User is not a Customer!");
        }

        return (Customer)this.userBean.getByUsername(getUser());
    }

    /**
     * Returns the Customer entity related to the logged user
     * @return
     */
    public Manager getManager() {
        if (!this.isLogged()) {
            throw new LoginBeanException("User is not logged!");
        }
        if (!this.isManager()) {
            throw new LoginBeanException("User is not a Manager!");
        }

        return (Manager)this.userBean.getByUsername(getUser());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
