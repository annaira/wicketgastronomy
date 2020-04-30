package helloworld.articles;

import helloworld.BaseWebPage;
import helloworld.services.ArticleService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ModifyArticlePage extends BaseWebPage {

    public ModifyArticlePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final EditArticle editArticle = new EditArticle("editArticle");
        final Long articleId = getPageParameters().get("id").to(Long.class);
        editArticle.setArticle(ServiceRegistry.get(ArticleService.class).get(articleId));
        add(editArticle);
    }

}
