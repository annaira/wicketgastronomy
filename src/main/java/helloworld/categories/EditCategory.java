package helloworld.categories;

import helloworld.EntityModel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

public class EditCategory extends Panel {

    private final Form<Category> form = new Form<Category>("form") {

        @Override
        protected void onSubmit() {
            super.onSubmit();
            ServiceRegistry.get(CategoryService.class).save(this.getModelObject());
        }
    };

    private final ValidationErrorFeedbackPanel validationFeedback;

    public EditCategory(String id) {
        super(id);
        this.form.setModel(new CompoundPropertyModel<>(new EntityModel<>(CategoryService.class)));
        validationFeedback = new ValidationErrorFeedbackPanel("validationFeedback");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(validationFeedback);
        add(form);
        form.add(new TextField<String>("name").add(new PropertyValidator<>()));
        form.add(new TextField<String>("imageUrl").add(new PropertyValidator<>()));
        form.add(new AjaxSubmitLink("save") {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                findParent(ModalWindow.class).close(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target);
                target.add(EditCategory.this.validationFeedback);
            }

        });
    }

    EditCategory setCategory(Category category) {
        this.form.setModelObject(category);
        return this;
    }

}
