
package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.UserDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.User;
import java.security.Principal;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
@RolesAllowed(value="Manager")
@DeclareRoles(value="Manager")
public class UserSessionBean {

    @EJB
    private UserDao userDao;
    
    public boolean isUsernameFree(String username) {
        return this.userDao.findByUsername(username) == null;
    }

    public User getByUsername(String username) {
        return this.userDao.findByUsername(username);
    }

    @RolesAllowed({"Customer", "Manager"})
    public User getLoggedUser() {
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (p != null) {
            String username = p.getName();
            return this.getByUsername(username);
        }
        return null;
    }
 
}
