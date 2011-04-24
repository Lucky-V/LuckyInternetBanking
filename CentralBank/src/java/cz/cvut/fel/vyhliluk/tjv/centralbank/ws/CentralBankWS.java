
package cz.cvut.fel.vyhliluk.tjv.centralbank.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Lucky
 */
@WebService()
public class CentralBankWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addTransfer")
    public ResponseDTO addTransfer(TransferDTO transfer) {
        ObjectFactory fact = new ObjectFactory();
        ResponseDTO res = fact.createResponseDTO();
        res.code = 0;
        res.description = "OK";

        return res;
    }

}
