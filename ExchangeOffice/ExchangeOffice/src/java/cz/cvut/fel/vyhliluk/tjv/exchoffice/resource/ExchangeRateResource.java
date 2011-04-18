package cz.cvut.fel.vyhliluk.tjv.exchoffice.resource;

import cz.cvut.fel.vyhliluk.tjv.exchoffice.converter.ExchangeRateConverter;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Date: 18.4.2011
 * Time: 14:31:35
 * @author Lucky
 */
@Path("exchangerate/{name}")
@Stateless
public class ExchangeRateResource {

    @Context
    private UriInfo uriInfo;

    @PersistenceContext
    private EntityManager em;

    @GET
    @Produces(value="application/xml")
    public ExchangeRateConverter read(@PathParam("id") String name) {
        return new ExchangeRateConverter("CZK", new BigDecimal("16.4321"));
    }

}
