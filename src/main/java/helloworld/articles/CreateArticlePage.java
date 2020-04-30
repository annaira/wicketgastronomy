package helloworld.articles;

import helloworld.BaseWebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class CreateArticlePage extends BaseWebPage {

    public CreateArticlePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new EditArticle("editArticle"));
    }

}
