package jp.co.city.mag.ping.web.ui.page;

import jabara.wicket.ComponentCssHeaderItem;
import jabara.wicket.IAjaxCallback;
import jabara.wicket.JavaScriptUtil;

import java.io.Serializable;

import javax.inject.Inject;

import jp.co.city.mag.ping.entity.EPingCategory;
import jp.co.city.mag.ping.service.Duplicate;
import jp.co.city.mag.ping.service.IPingCategoryService;
import jp.co.city.mag.ping.web.ui.component.PingCategoryPanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 *
 */
@SuppressWarnings("synthetic-access")
public class TopPage extends RestrictedPageBase {
    private static final long       serialVersionUID = -3625945544404875397L;

    @Inject
    IPingCategoryService            pingCategoryService;

    private final Handler           handler          = new Handler();

    private WebMarkupContainer      categoriesContainer;
    private ListView<EPingCategory> categories;
    private Link<?>                 refresher;
    private AjaxLink<?>             adder;
    private FeedbackPanel           feedback;

    private int                     categoryCount    = 0;

    /**
     * 
     */
    public TopPage() {
        this.add(getCategoriesContainer());
        this.add(getRefresher());
        this.add(getFeedback());
        this.add(getAdder());
    }

    /**
     * @see jp.co.city.mag.ping.web.ui.page.WebPageBase#renderHead(org.apache.wicket.markup.head.IHeaderResponse)
     */
    @Override
    public void renderHead(final IHeaderResponse pResponse) {
        super.renderHead(pResponse);
        pResponse.render(ComponentCssHeaderItem.forType(TopPage.class));

        pResponse.render(JavaScriptUtil.forJQuery1_9_1ReferenceHeaderItem());
        pResponse.render(JavaScriptHeaderItem.forReference( //
                new JavaScriptResourceReference( //
                        TopPage.class //
                        , "min/" + TopPage.class.getSimpleName() + ".min.js"))); //$NON-NLS-1$ //$NON-NLS-2$

        // チェックボックスをスイッチ形式で表示するために必要なファイル.
        // http://www.bootstrap-switch.org
        pResponse.render(JavaScriptHeaderItem.forReference( //
                new JavaScriptResourceReference( //
                        TopPage.class //
                        , "bootstrapSwitch/js/bootstrap-switch.min.js"))); //$NON-NLS-1$
        pResponse.render(CssHeaderItem.forReference( //
                new CssResourceReference( //
                        TopPage.class //
                        , "bootstrapSwitch/css/bootstrap-switch.min.css"))); //$NON-NLS-1$
    }

    /**
     * @see jp.co.city.mag.ping.web.ui.page.WebPageBase#getTitleLabelModel()
     */
    @Override
    protected IModel<String> getTitleLabelModel() {
        return Model.of("Top"); //$NON-NLS-1$
    }

    @SuppressWarnings("serial")
    private AjaxLink<?> getAdder() {
        if (this.adder == null) {
            this.adder = new IndicatingAjaxLink<Object>("adder") { //$NON-NLS-1$
                @Override
                public void onClick(final AjaxRequestTarget pTarget) {
                    TopPage.this.handler.onCategoryAdd(pTarget);
                }
            };
        }
        return this.adder;
    }

    @SuppressWarnings("serial")
    private ListView<EPingCategory> getCategories() {
        if (this.categories == null) {
            this.categories = new ListView<EPingCategory>("categories", this.pingCategoryService.getAll()) { //$NON-NLS-1$

                @SuppressWarnings("nls")
                @Override
                protected void populateItem(final ListItem<EPingCategory> pItem) {
                    final IAjaxCallback onUpdate = new IAjaxCallback() {
                        @Override
                        public void call(final AjaxRequestTarget pTarget) {
                            TopPage.this.handler.onCategoryUpdate(pTarget);
                        }
                    };
                    final IAjaxCallback onDelete = new IAjaxCallback() {
                        @Override
                        public void call(final AjaxRequestTarget pTarget) {
                            TopPage.this.handler.onCategoryDelete(pTarget, pItem.getModelObject());
                        }
                    };
                    pItem.add(new PingCategoryPanel("category", pItem.getModelObject(), onUpdate, onDelete));
                }
            };
        }
        return this.categories;
    }

    private WebMarkupContainer getCategoriesContainer() {
        if (this.categoriesContainer == null) {
            this.categoriesContainer = new WebMarkupContainer("categoriesContainer"); //$NON-NLS-1$
            this.categoriesContainer.add(getCategories());
        }
        return this.categoriesContainer;
    }

    private FeedbackPanel getFeedback() {
        if (this.feedback == null) {
            this.feedback = new FeedbackPanel("feedback"); //$NON-NLS-1$
        }
        return this.feedback;
    }

    private Link<?> getRefresher() {
        if (this.refresher == null) {
            this.refresher = new BookmarkablePageLink<>("refresher", this.getClass()); //$NON-NLS-1$
        }
        return this.refresher;
    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = 8826180320287426527L;

        void onCategoryAdd(final AjaxRequestTarget pTarget) {
            final EPingCategory newCategory = new EPingCategory("Category_" + ++TopPage.this.categoryCount); //$NON-NLS-1$
            getCategories().getModelObject().add(newCategory);
            try {
                TopPage.this.pingCategoryService.insert(newCategory);
            } catch (final Duplicate e) {
                error("カテゴリ名が重複しています."); //$NON-NLS-1$
                pTarget.add(getFeedback());
            }
            pTarget.add(getFeedback());
            pTarget.add(getCategoriesContainer());
            appendSetBootstrapSwitchScript(pTarget);
        }

        void onCategoryDelete(final AjaxRequestTarget pTarget, final EPingCategory pPingCategory) {
            getCategories().getModelObject().remove(pPingCategory);
            pTarget.add(getCategoriesContainer());
            appendSetBootstrapSwitchScript(pTarget);
        }

        void onCategoryUpdate(final AjaxRequestTarget pTarget) {
            pTarget.add(getFeedback());
        }

        private void appendSetBootstrapSwitchScript(final AjaxRequestTarget pTarget) {
            pTarget.appendJavaScript("setBootstrapSwitch();"); //$NON-NLS-1$
        }

    }
}
