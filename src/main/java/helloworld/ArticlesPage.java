package helloworld;

import helloworld.entities.Article;
import helloworld.services.ArticleService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

public class ArticlesPage extends BaseEntitiesPage {

    private final DataView<Article> articles;

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
                final AttributeAppender srcAppender = new AttributeAppender("src", new PropertyModel<>(new EntityModel<>(article.getId(), ArticleService.class), "imageUrl"));
                item.add(new WebMarkupContainer("image").add(srcAppender));
                item.add(new Label("validFrom"));
                item.add(new Label("validTo"));
            }
        };
    }

    @Override
    IPageable getPageable() {
        return articles;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        articles.setItemsPerPage(3);
        add(articles);
    }

}
