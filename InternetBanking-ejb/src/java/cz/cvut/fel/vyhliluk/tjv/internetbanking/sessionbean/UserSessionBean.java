
package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.UserDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class UserSessionBean {

    @EJB
    private UserDao userDao;
    
    public boolean isUsernameFree(String username) {
        return this.userDao.findByUsername(username) == null;
    }

    public User getByUsername(String username) {
        return this.userDao.findByUsername(username);
    }
 
}
