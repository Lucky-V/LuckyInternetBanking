/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lucky
 */
@Stateless
public class CurrencySessionBean implements CurrencySessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Currency> getAllCurencies() {
        Query q = this.em.createNamedQuery("Currency.getAll");
        return q.getResultList();
    }

    @Override
    public void updateCurrency(Currency c) {
        this.em.merge(c);
    }

    @Override
    public void deleteCurrency(String code) {
        Currency c = this.em.find(Currency.class, code);
        if (c != null) {
            this.em.remove(c);
        }
    }

    @Override
    public Currency getByCode(String code) {
        Query q = this.em.createNamedQuery("Currency.getByCode");
        q.setParameter("code", code);
        try {
            return (Currency) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
