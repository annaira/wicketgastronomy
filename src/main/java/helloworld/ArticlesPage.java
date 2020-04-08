package helloworld;

import helloworld.entities.Article;
import helloworld.services.ArticleService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

public class ArticlesPage extends BaseEntitiesPage {
    public ArticlesPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final List<Article> articleList = new ArrayList<>(ServiceRegistry.get(ArticleService.class).listAll());

        final IDataProvider<Article> dataProvider = new ListDataProvider<>(articleList);

        final DataView<Article> categories = new DataView<Article>("articles", dataProvider) {
            @Override
            protected void populateItem(Item<Article> item) {
                item.add(new Label("category", item.getModelObject().getCategory().getName()));
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Label("description", item.getModelObject().getDescription()));
                item.add(new Label("price", item.getModelObject().getFormattedPrice()));
                item.add(new WebMarkupContainer("image").add(new AttributeAppender("src", item.getModelObject().getImageUrl())));
                item.add(new Label("validFrom", item.getModelObject().getFormattedValidFrom()));
                item.add(new Label("validTo", item.getModelObject().getFormattedValidTo()));
            }
        };
        categories.setItemsPerPage(3);
        final PagingNavigation navigation = new PagingNavigation("navigation", categories);
        add(categories);
        add(navigation);
    }

}
