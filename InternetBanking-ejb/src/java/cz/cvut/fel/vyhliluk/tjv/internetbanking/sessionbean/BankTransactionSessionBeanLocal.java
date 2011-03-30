package cz.cvut.fel.vyhliluk.tjv.internetbanking.sessionbean;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.BankTransaction;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Lucky
 */
@Local
public interface BankTransactionSessionBeanLocal {

    void addTransaction(BankTransaction transaction);

    List<BankTransaction> getAllTransactions();

    List<BankTransaction> getAllTransactionsLimited(int from, int to);
}
