package helloworld.articles;

import helloworld.EntityModel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.services.ArticleService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.UrlValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EditArticle extends Panel {

    private final Form<Article> form = new Form<Article>("form") {

        @Override
        protected void onSubmit() {
            super.onSubmit();
            ServiceRegistry.get(ArticleService.class).save(form.getModelObject());
            setResponsePage(ArticlesPage.class);
        }
    };

    public EditArticle(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initializeForm();
        form.setModelObject(new Article());
    }

    private void initializeForm() {
        add(form);
        add(new ValidationErrorFeedbackPanel("validationFeedback"));
        form.setModel(new CompoundPropertyModel<>(new EntityModel<>(ArticleService.class)));
        form.add(new TextField<String>("name").setRequired(true).setLabel(Model.of("Name")));
        form.add(new DropDownChoice<>("category", new CategoryListModel(), new ChoiceRenderer<>("name", "id")).add(new PropertyValidator<>()));
        form.add(new TextArea<String>("description").setRequired(true).setLabel(Model.of("Beschreibung")));
        form.add(new TextField<>("price").setRequired(true).setLabel(Model.of("Preis")).add(new RangeValidator<>(BigDecimal.ZERO, new BigDecimal("20"))));
        form.add(new TextField<>("validFrom").setLabel(Model.of("Gültig ab")).add(RangeValidator.maximum(LocalDate.now().plusMonths(3))));
        form.add(new TextField<>("validTo").setLabel(Model.of("Gültig bis")).add(RangeValidator.minimum(LocalDate.now().plusDays(1))));
        form.add(new TextField<String>("imageUrl").setRequired(true).setLabel(Model.of("Bild-URL")).add(new UrlValidator()));
    }

}
