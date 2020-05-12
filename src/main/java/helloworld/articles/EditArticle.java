package helloworld.articles;

import helloworld.EntityModel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.services.ArticleService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableChoiceLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableMultiLineLabel;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
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
    private final AjaxEditableLabel<String> nameField;

    public EditArticle(String id) {
        super(id);
        this.form.setModel(new CompoundPropertyModel<>(new EntityModel<>(ArticleService.class)));
        this.nameField = new AjaxEditableLabel<>("name");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initializeForm();
        this.form.add(new ExternalLink("help", new Model<String>() {
            @Override
            public String getObject() {
                return "https://de.wikipedia.org/wiki/" + nameField.getModelObject();
            }
        }));
    }

    private void initializeForm() {
        add(form);
        add(new ValidationErrorFeedbackPanel("validationFeedback"));

        form.add(nameField.setRequired(true).setLabel(Model.of("Name")));
        form.add(new AjaxEditableChoiceLabel<>("category", null, new CategoryListModel(), new ChoiceRenderer<>("name", "id")));
        form.add(new AjaxEditableMultiLineLabel<>("description").setRequired(true).setLabel(Model.of("Beschreibung")));
        form.add(new AjaxEditableLabel<>("price").setRequired(true).setLabel(Model.of("Preis")).add(new RangeValidator<>(BigDecimal.ZERO, new BigDecimal("20"))));
        form.add(new AjaxEditableLabel<>("validFrom").setLabel(Model.of("Gültig ab")).add(RangeValidator.maximum(LocalDate.now().plusMonths(3))));
        form.add(new AjaxEditableLabel<>("validTo").setLabel(Model.of("Gültig bis")).add(RangeValidator.minimum(LocalDate.now().plusDays(1))));
        form.add(new AjaxEditableLabel<String>("imageUrl").setRequired(true).setLabel(Model.of("Bild-URL")).add(new UrlValidator()));
    }

    public Component setArticle(Article article) {
        form.setModelObject(article);
        return this;
    }
}
