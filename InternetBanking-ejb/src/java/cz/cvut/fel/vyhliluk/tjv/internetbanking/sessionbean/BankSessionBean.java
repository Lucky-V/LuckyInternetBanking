package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Bank;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
public class BankSessionBean implements BankSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void updateBankCode(Bank b) {
        this.em.merge(b);
    }

    @Override
    public List<Bank> getAllBanks() {
        Query q = this.em.createNamedQuery("Bank.findAll");
        return q.getResultList();
    }

    @Override
    public void removeBank(Integer code) {
        Bank bank = this.em.find(Bank.class, code);
        if (bank != null) {
            this.em.remove(bank);
        }
    }

    @Override
    public Bank getByCode(Integer code) {
        return this.em.find(Bank.class, code);
    }

}
