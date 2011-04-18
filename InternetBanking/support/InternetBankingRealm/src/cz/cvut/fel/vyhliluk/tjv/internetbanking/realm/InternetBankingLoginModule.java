package cz.cvut.fel.vyhliluk.tjv.internetbanking.realm;

import com.sun.appserv.security.AppservPasswordLoginModule;
import javax.security.auth.login.LoginException;

/**
 * Date: 6.4.2011
 * Time: 9:11:55
 * @author Lucky
 */
public class InternetBankingLoginModule extends AppservPasswordLoginModule {

    @Override
    protected void authenticateUser() throws LoginException {
        if ("luk".equals(_username) && "pass".equals(_password)) {
            String[] grpList = {"koordinatori"};
            commitUserAuthentication(grpList);
        } else {
            throw new LoginException("Bad username or password.");
        }
    }



}
