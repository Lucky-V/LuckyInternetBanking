package cz.cvut.fel.vyhliluk.tjv.centralbank.ws;

import cz.cvut.fel.vyhliluk.tjv.centralbank.entity.Transfer;
import java.util.Date;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lucky
 */
@WebService()
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CentralBankWS {

    @PersistenceContext
    private EntityManager em;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addTransfer")
    public ResponseDTO addTransfer(TransferDTO transfer) {
        try {
            Date d = new Date(transfer.getDateTime().getMillisecond());
            Transfer trans = new Transfer();
            trans.setAccountFrom(transfer.getAccountFrom());
            trans.setAccountTo(transfer.getAccountTo());
            trans.setBankFrom(transfer.getBankFrom());
            trans.setBankTo(transfer.getBankTo());
            trans.setAmount(transfer.getAmount());
            trans.setCurrency(transfer.getCurrency());
            trans.setDateTime(d);
            trans.setDescription(transfer.getDescription());

            this.em.persist(trans);

            ObjectFactory fact = new ObjectFactory();
            ResponseDTO res = fact.createResponseDTO();
            res.code = 0;
            res.description = "OK";

            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            ObjectFactory fact = new ObjectFactory();
            ResponseDTO res = fact.createResponseDTO();
            res.code = 1;
            res.description = "Error";

            return res;
        }
    }
}
