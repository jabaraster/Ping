/**
 * 
 */
package jp.co.city.mag.ping.web.ui.component;

import jabara.general.ArgUtil;
import jabara.wicket.IAjaxCallback;

import java.io.Serializable;

import javax.inject.Inject;

import jp.co.city.mag.ping.entity.EPingCategory;
import jp.co.city.mag.ping.entity.EPingCategory.State;
import jp.co.city.mag.ping.entity.EPingCategory_;
import jp.co.city.mag.ping.service.Duplicate;
import jp.co.city.mag.ping.service.IPingCategoryService;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * @author jabaraster
 */
@SuppressWarnings("synthetic-access")
public class PingCategoryPanel extends Panel {
    private static final long         serialVersionUID = -7879993057205860194L;

    @Inject
    IPingCategoryService              pingCategoryService;

    private final Handler             handler          = new Handler();

    private final EPingCategory       category;
    private final IAjaxCallback       onUpdate;
    private final IAjaxCallback       onDelete;

    private Form<?>                   form;
    private AjaxEditableLabel<String> categoryLabel;
    private CheckBox                  state;
    private AjaxButton                submitter;
    private AjaxButton                deleter;

    /**
     * @param pId -
     * @param pCategory -
     * @param pOnUpdate -
     * @param pOnDelete -
     */
    public PingCategoryPanel( //
            final String pId //
            , final EPingCategory pCategory //
            , final IAjaxCallback pOnUpdate //
            , final IAjaxCallback pOnDelete //
    ) {
        super(pId);
        this.category = ArgUtil.checkNull(pCategory, "pCategory"); //$NON-NLS-1$
        this.onUpdate = pOnUpdate;
        this.onDelete = pOnDelete;
        this.add(getForm());
    }

    private AjaxEditableLabel<String> getCategoryLabel() {
        if (this.categoryLabel == null) {
            final IModel<String> model = new PropertyModel<String>(this.category, EPingCategory_.category.getName());
            this.categoryLabel = new AjaxEditableLabel<>("categoryLabel", model); //$NON-NLS-1$
        }
        return this.categoryLabel;
    }

    @SuppressWarnings("serial")
    private AjaxButton getDeleter() {
        if (this.deleter == null) {
            this.deleter = new IndicatingAjaxButton("deleter") { //$NON-NLS-1$
                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    PingCategoryPanel.this.handler.onDelete(pTarget);
                }

                @Override
                protected void updateAjaxAttributes(final AjaxRequestAttributes pAttributes) {
                    super.updateAjaxAttributes(pAttributes);
                    final AjaxCallListener ajaxCallListener = new AjaxCallListener();
                    ajaxCallListener.onPrecondition("return confirm('この操作は取り消せません！本当に削除してよろしいですか？');"); //$NON-NLS-1$
                    pAttributes.getAjaxCallListeners().add(ajaxCallListener);
                }
            };
        }
        return this.deleter;
    }

    private Form<?> getForm() {
        if (this.form == null) {
            this.form = new Form<>("form"); //$NON-NLS-1$
            this.form.add(getCategoryLabel());
            this.form.add(getState());
            this.form.add(getSubmitter());
            this.form.add(getDeleter());
        }
        return this.form;
    }

    @SuppressWarnings("serial")
    private CheckBox getState() {
        if (this.state == null) {
            this.state = new CheckBox("state", new CheckBoxModel()); //$NON-NLS-1$
            this.state.add(new OnChangeAjaxBehavior() {
                @Override
                protected void onUpdate(@SuppressWarnings("unused") final AjaxRequestTarget pTarget) {
                    // 入力値をModelに格納したいだけなので、ここでの処理は特になし.
                }
            });
        }
        return this.state;
    }

    @SuppressWarnings("serial")
    private AjaxButton getSubmitter() {
        if (this.submitter == null) {
            this.submitter = new IndicatingAjaxButton("submitter") { //$NON-NLS-1$
                @Override
                protected void onSubmit(final AjaxRequestTarget pTarget, @SuppressWarnings("unused") final Form<?> pForm) {
                    PingCategoryPanel.this.handler.onUpdate(pTarget);
                }
            };
        }
        return this.submitter;
    }

    private class CheckBoxModel implements IModel<Boolean> {
        private static final long serialVersionUID = -3398568811459226838L;

        @Override
        public void detach() {
            // 処理なし
        }

        @Override
        public Boolean getObject() {
            return Boolean.valueOf(PingCategoryPanel.this.category.getState() == State.ON);
        }

        @Override
        public void setObject(final Boolean pObject) {
            ArgUtil.checkNull(pObject, "pObject"); //$NON-NLS-1$
            PingCategoryPanel.this.category.setState(pObject.booleanValue() ? State.ON : State.OFF);
        }

    }

    private class Handler implements Serializable {
        private static final long serialVersionUID = -1296815141696374857L;

        void onDelete(final AjaxRequestTarget pTarget) {
            PingCategoryPanel.this.pingCategoryService.delete(PingCategoryPanel.this.category);
            if (PingCategoryPanel.this.onDelete != null) {
                PingCategoryPanel.this.onDelete.call(pTarget);
            }
        }

        void onUpdate(final AjaxRequestTarget pTarget) {
            try {
                PingCategoryPanel.this.pingCategoryService.insertOrUpdate(PingCategoryPanel.this.category);
            } catch (final Duplicate e) {
                error("カテゴリ名が重複しています."); //$NON-NLS-1$
            }
            if (PingCategoryPanel.this.onUpdate != null) {
                PingCategoryPanel.this.onUpdate.call(pTarget);
            }
        }
    }
}
