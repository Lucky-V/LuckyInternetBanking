/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.fel.vyhliluk.tjv.internetbanking.backingbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean.BankTransactionSessionBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Lucky
 */
@ManagedBean(name="transactions")
@RequestScoped
public class TransactionsBean {

    @EJB
    private BankTransactionSessionBean bankTransBean;

    //private LazyDataModel<BankTransaction> transactionsLazyModel;

    public List<BankTransaction> getAllTransactions() {
        return this.bankTransBean.getAllTransactions();
    }

    /**public LazyDataModel<BankTransaction> getTransactionsLazyModel() {
        this.transactionsLazyModel = new LazyDataModel<BankTransaction>() {

            @Override
            public List<BankTransaction> load(int first, int size, String sortField, boolean sortOrder, Map<String, String> filters) {
                List<BankTransaction> res = new ArrayList<BankTransaction>();//bankTransBean.getAllTransactionsLimited(first, size);
                BankTransaction t = new BankTransaction();
                t.setAmountFrom(BigDecimal.ONE);
                res.add(t);
                return res;
            }

            @Override
            public void setRowIndex(int rowIndex) {
                super.setRowIndex(rowIndex);
            }
        };

        return transactionsLazyModel;
    }

    public void setTransactionsLazyModel(LazyDataModel<BankTransaction> transactionsLazyModel) {
        this.transactionsLazyModel = transactionsLazyModel;
    }**/

}
