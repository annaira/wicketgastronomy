package helloworld.articles;

import helloworld.BaseEntitiesPage;
import helloworld.EntityModel;
import helloworld.SuccessFeedbackPanel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.categories.Category;
import helloworld.services.ArticleService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.form.datetime.LocalDateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;
import org.apache.wicket.validation.validator.UrlValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.FormatStyle;

public class ArticlesPage extends BaseEntitiesPage {

    private final DataView<Article> articles;

    private final Form<Article> form = new Form<Article>("form") {

        @Override
        protected void onSubmit() {
            super.onSubmit();
            form.setVisible(false);
            ServiceRegistry.get(ArticleService.class).save(form.getModelObject());
            form.success("Artikel wurde gespeichert");
        }
    };

    public ArticlesPage(PageParameters parameters) {
        super(parameters);
        final IDataProvider<Article> dataProvider = new ArticlesDataProvider();

        articles = new DataView<Article>("articles", dataProvider) {
            @Override
            protected void populateItem(Item<Article> item) {
                final Article article = item.getModelObject();
                item.setModel(new CompoundPropertyModel<>(item.getModel()));
                item.add(new Label("category.name"));
                item.add(new Label("name"));
                item.add(new Label("description"));
                item.add(new Label("price"));
                final AttributeAppender srcAppender = new AttributeAppender("src", new PropertyModel<>(new EntityModel<>(article, ArticleService.class), "imageUrl"));
                item.add(new WebMarkupContainer("image").add(srcAppender));
                item.add(new Label("validFrom"));
                item.add(new Label("validTo"));
            }
        };
    }

    @Override
    protected IPageable getPageable() {
        return articles;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(this.articles);
        add(new Link<String>("newArticle") {

            @Override
            public void onClick() {
                form.setVisible(true);
                form.setModelObject(new Article());
            }
        });
        initializeForm();
    }

    private void initializeForm() {
        form.setVisible(false);
        add(form);
        add(new ValidationErrorFeedbackPanel("validationFeedback"));
        add(new SuccessFeedbackPanel("successFeedback"));
        form.setModel(new CompoundPropertyModel<>(new EntityModel<>(ArticleService.class)));
        form.add(new TextField<String>("name").setRequired(true).setLabel(Model.of("Name")));
        form.add(new DropDownChoice<Category>("category", new CategoryListModel(), new ChoiceRenderer<Category>("name", "id")).add(new PropertyValidator<>()));
        form.add(new TextArea<String>("description").setRequired(true).setLabel(Model.of("Beschreibung")));
        form.add(new TextField<>("price").setRequired(true).setLabel(Model.of("Preis")).add(new RangeValidator<>(BigDecimal.ZERO, new BigDecimal("20"))));
        form.add(new LocalDateTextField("validFrom", FormatStyle.SHORT).setLabel(Model.of("Gültig ab")).add(RangeValidator.maximum(LocalDate.now().plusMonths(3))));
        form.add(new LocalDateTextField("validTo", FormatStyle.SHORT).setLabel(Model.of("Gültig bis")).add(RangeValidator.minimum(LocalDate.now().plusDays(1))));
        form.add(new TextField<String>("imageUrl").setRequired(true).setLabel(Model.of("Bild-URL")).add(new UrlValidator()));
    }


}