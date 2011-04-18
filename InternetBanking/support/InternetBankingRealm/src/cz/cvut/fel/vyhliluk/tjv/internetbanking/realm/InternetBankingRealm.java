package cz.cvut.fel.vyhliluk.tjv.internetbanking.realm;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Date: 6.4.2011
 * Time: 9:39:18
 * @author Lucky
 */
public class InternetBankingRealm extends AppservRealm {

    private String jaasCtxName;

    @Override
    protected void init(Properties props) throws BadRealmException, NoSuchRealmException {
        this.jaasCtxName = props.getProperty("jaas-context", "InternetBankingRealm");
    }

    @Override
    public synchronized String getJAASContext() {
        return this.jaasCtxName;
    }



    @Override
    public String getAuthType() {
        return "InternetBankingAuth";
    }

    @Override
    public Enumeration getGroupNames(String username) throws InvalidOperationException, NoSuchUserException {
        return (Enumeration) new LinkedList<String>();
    }

}
