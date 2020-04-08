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
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

public class ArticlesPage extends BaseEntitiesPage {

    private final DataView<Article> articles;

    public ArticlesPage(PageParameters parameters) {
        super(parameters);
        final List<Article> articleList = new ArrayList<>(ServiceRegistry.get(ArticleService.class).listAll());

        final IDataProvider<Article> dataProvider = new ListDataProvider<>(articleList);

        articles = new DataView<Article>("articles", dataProvider) {
            @Override
            protected void populateItem(Item<Article> item) {
                Article article = item.getModelObject();
                item.add(new Label("category", article.getCategory().getName()));
                item.add(new Label("name", article.getName()));
                item.add(new Label("description", article.getDescription()));
                item.add(new Label("price", article.getFormattedPrice()));
                item.add(new WebMarkupContainer("image").add(new AttributeAppender("src", article.getImageUrl())));
                item.add(new Label("validFrom", article.getFormattedValidFrom()));
                item.add(new Label("validTo", article.getFormattedValidTo()));
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
