package helloworld.articles;

import helloworld.EntityModel;
import helloworld.services.ArticleService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

public class ArticlesDataProvider extends SortableDataProvider<Article, Void> {

    @Override
    public Iterator<? extends Article> iterator(long first, long count) {
        return ServiceRegistry.get(ArticleService.class).listAll().iterator();
    }

    @Override
    public long size() {
        return ServiceRegistry.get(ArticleService.class).listAll().size();
    }

    @Override
    public IModel<Article> model(Article article) {
        return new EntityModel<>(article, ArticleService.class);
    }
}
