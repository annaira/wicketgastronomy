package helloworld.categories;

import helloworld.BaseWebPage;
import helloworld.EntityModel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class CreateCategoryPage extends BaseWebPage {

    private final Form<Category> form = new Form<Category>("form") {

        @Override
        protected void onSubmit() {
            super.onSubmit();
            ServiceRegistry.get(CategoryService.class).save(this.getModelObject());
            setResponsePage(CategoriesPage.class);
        }
    };

    public CreateCategoryPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initializeForm();
    }

    private void initializeForm() {
        super.onInitialize();
        add(new ValidationErrorFeedbackPanel("validationFeedback"));
        form.setModel(new CompoundPropertyModel<>(new EntityModel<>(CategoryService.class)));
        form.setModelObject(new Category());
        add(form);
        form.add(new TextField<String>("name").add(new PropertyValidator<>()));
        form.add(new TextField<String>("imageUrl").add(new PropertyValidator<>()));
    }
}
