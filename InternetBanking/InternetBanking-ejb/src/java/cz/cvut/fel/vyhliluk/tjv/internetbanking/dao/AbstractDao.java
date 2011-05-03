package cz.cvut.fel.vyhliluk.tjv.internetbanking.dao;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.exception.EntityAlreadyUpdatedException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Date: 13.4.2011
 * Time: 13:59:38
 * @author Lucky
 */
public class AbstractDao<T, I> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> clazz;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<T> findAll() {
        Query q = this.em.createNamedQuery(this.clazz.getSimpleName() +".findAll");
        return q.getResultList();
    }

    public T findById(I id) {
        return this.em.find(this.clazz, id);
    }

    public void create(T entity) {
        this.em.persist(entity);
    }

    public void update(T entity) throws EntityAlreadyUpdatedException {
        try {
            this.em.merge(entity);
        } catch (OptimisticLockException ex) {
            throw new EntityAlreadyUpdatedException("Customer has been already updated");
        }
    }

    public void delete(T entity) {
        this.em.remove(entity);
    }

    public void deleteById(I id) {
        T entity = this.findById(id);
        this.em.remove(entity);
    }

    public void refresh(T entity) {
        T e = this.em.merge(entity);
        this.em.refresh(e);
    }

}
