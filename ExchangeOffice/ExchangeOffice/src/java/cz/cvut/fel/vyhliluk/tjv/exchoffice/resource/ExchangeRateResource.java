package cz.cvut.fel.vyhliluk.tjv.exchoffice.resource;

import cz.cvut.fel.vyhliluk.tjv.exchoffice.converter.ExchangeRateConverter;
import cz.cvut.fel.vyhliluk.tjv.exchoffice.entity.ExchangeRate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @PersistenceContext
    private EntityManager em;
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = "application/xml")
    public ExchangeRateConverter read(@PathParam("name") String name) {
        try {
            String currencyFrom = name.substring(0, 3);
            String currencyTo = name.substring(3);
            ExchangeRate from = this.findByCode(currencyFrom);
            ExchangeRate to = this.findByCode(currencyTo);
            BigDecimal rate = to.getRate().divide(from.getRate(), RoundingMode.HALF_UP);
            return new ExchangeRateConverter(currencyFrom, currencyTo, rate);
        } catch (Exception ex) {
            return new ExchangeRateConverter("", "", new BigDecimal("0"));
        }
    }

    private ExchangeRate findByCode(String code) {
        Query q = this.em.createNamedQuery("ExchangeRate.findByCode");
        q.setParameter("code", code);
        return (ExchangeRate) q.getSingleResult();
    }
}
