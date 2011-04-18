
package cz.cvut.fel.vyhliluk.tjv.centralbank.ws;

import java.math.BigDecimal;
import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebParam;
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
    @WebMethod(operationName = "transfer")
    public int transfer(
            @WebParam(name="bankFrom") final Integer bankFrom,
            @WebParam(name="accountFrom") final Long accountFrom,
            @WebParam(name="bankTo") final Integer bankTo,
            @WebParam(name="accountTo") final Long accountTo,
            @WebParam(name="amount") final BigDecimal amount,
            @WebParam(name="currency") final String currency,
            @WebParam(name="description") final String description,
            @WebParam(name="date") final Date dateTime) {

        return 0;
    }

}
