package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.manager;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.dao.BankDao;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Bank;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Lucky
 */
@Stateless
@LocalBean
@RolesAllowed(value="Manager")
public class BankSessionBean {

    @EJB
    private BankDao bankDao;

    public void updateBankCode(Bank b) {
        try {
            this.bankDao.update(b);
        } catch (EntityAlreadyUpdatedException ex) {
            Logger.getLogger(BankSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Bank> getAllBanks() {
        return this.bankDao.findAll();
    }

    public void removeBank(Integer code) {
        this.bankDao.deleteById(code);
    }

    public Bank getByCode(Integer code) {
        return this.bankDao.findById(code);
    }
}
