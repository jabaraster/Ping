package jp.co.city.mag.ping.web.ui.page;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import jp.co.city.mag.ping.Environment;

/**
 *
 */
public abstract class RestrictedPageBase extends WebPageBase {

    private Label             applicationName;
    private Link<?>           goTop;
    private Link<?>           goLogout;

    /**
     * 
     */
    protected RestrictedPageBase() {
        this(new PageParameters());
        this.add(getGoTop());
        this.add(getGoLogout());
    }

    /**
     * @param pParameters
     */
    protected RestrictedPageBase(final PageParameters pParameters) {
        super(pParameters);
    }

    private Label getApplicationName() {
        if (this.applicationName == null) {
            this.applicationName = new Label("applicationName", Environment.getApplicationName()); //$NON-NLS-1$
        }
        return this.applicationName;
    }

    private Link<?> getGoLogout() {
        if (this.goLogout == null) {
            this.goLogout = new BookmarkablePageLink<>("goLogout", LogoutPage.class); //$NON-NLS-1$
        }
        return this.goLogout;
    }

    private Link<?> getGoTop() {
        if (this.goTop == null) {
            this.goTop = new BookmarkablePageLink<>("goTop", Application.get().getHomePage()); //$NON-NLS-1$
            this.goTop.add(getApplicationName());
        }
        return this.goTop;
    }
}
