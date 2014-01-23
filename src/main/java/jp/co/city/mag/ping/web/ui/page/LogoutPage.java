package jp.co.city.mag.ping.web.ui.page;

import jp.co.city.mag.ping.web.ui.AppSession;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;

/**
 * 
 */
public class LogoutPage extends WebPage {
    private static final long serialVersionUID = -5266147872187911990L;

    /**
     * 
     */
    public LogoutPage() {
        AppSession.get().invalidateNow();
        throw new RestartResponseException(LoginPage.class);
    }
}
