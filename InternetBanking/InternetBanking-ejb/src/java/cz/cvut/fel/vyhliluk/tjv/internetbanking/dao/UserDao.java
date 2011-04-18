
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class UserDao extends AbstractDao<User, Long> {

    public UserDao() {
        super(User.class);
    }

    public User findByUsername(String username) {
        Query q = this.em.createNamedQuery("User.findByUsername");
        q.setParameter("username", username);
        List<User> res = q.getResultList();
        if (res.isEmpty()) {
            return null;
        } else {
            return res.get(0);
        }
    }
    
    
 
}
