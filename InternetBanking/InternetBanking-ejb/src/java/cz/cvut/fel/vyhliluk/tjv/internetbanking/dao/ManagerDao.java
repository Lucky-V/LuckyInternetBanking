
package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Manager;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
public class ManagerDao extends AbstractDao<Manager, Long> {

    public ManagerDao() {
        super(Manager.class);
    }
    
    
 
}
