package jp.co.city.mag.ping.web.ui.page;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;

import jp.co.city.mag.ping.web.ui.AppSession;

/**
 * 
 */
public class LogoutPage extends WebPage {

    /**
     * 
     */
    public LogoutPage() {
        AppSession.get().invalidateNow();
        throw new RestartResponseException(LoginPage.class);
    }
}
