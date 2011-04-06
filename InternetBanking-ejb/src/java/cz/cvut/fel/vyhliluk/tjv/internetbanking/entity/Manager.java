package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.util.UserRole;
import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Date: 6.4.2011
 * Time: 11:13:18
 * @author Lucky
 */
@Entity
@DiscriminatorValue(value=UserRole.MANAGER)
public class Manager extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Manager[id=" + this.id + "]";
    }

}
