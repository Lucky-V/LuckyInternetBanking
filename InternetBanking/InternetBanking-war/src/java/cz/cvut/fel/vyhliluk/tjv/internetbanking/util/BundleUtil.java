package cz.cvut.fel.vyhliluk.tjv.internetbanking.util;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Date: 9.3.2011
 * Time: 15:46:20
 * @author Lucky
 */
public class BundleUtil {

    private static final String BUNDLE_NAME = "cz.cvut.fel.vyhliluk.tjv.internetbanking.messages.Messages";

    public static String getString(String key) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);

        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        } else {
            return "???"+ key +"???";
        }
    }

    public static void addOkMessage(String titleKey, String detailKey) {
        String title = getString(titleKey);
        String message = getString(detailKey);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, title, message));
    }

    public static void addErrMessage(String titleKey, String detailKey) {
        String title = getString(titleKey);
        String message = getString(detailKey);
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
    }

}
