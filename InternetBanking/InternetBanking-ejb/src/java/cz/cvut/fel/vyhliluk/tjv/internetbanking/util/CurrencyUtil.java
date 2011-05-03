package cz.cvut.fel.vyhliluk.tjv.internetbanking.util;

import cz.cvut.fel.vyhliluk.tjv.internetbanking.entity.Currency;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Date: 19.3.2011
 * Time: 11:24:55
 * @author Lucky
 */
public class CurrencyUtil {

    public static BigDecimal setCountScale(BigDecimal number) {
        return number.setScale(4, RoundingMode.HALF_UP);
    }

    public static BigDecimal setDbScale(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal setScale(BigDecimal number, Currency c) {
        return number.setScale(c.getDecimalDigits(), RoundingMode.HALF_UP);
    }

}
