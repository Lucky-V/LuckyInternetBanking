package cz.cvut.fel.vyhliluk.tjv.internetbanking.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Date: 8.3.2011
 * Time: 11:36:27
 * @author Lucky
 */
@Entity
public class Bank implements Serializable {
    @Id
    private Long code;

    private String name;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
