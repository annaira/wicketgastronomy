package helloworld;

import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BaseEntitiesPage extends BaseWebPage {

    public BaseEntitiesPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final PagingNavigation navigation = new PagingNavigation("navigation", getPageable());
        add(navigation);
    }

    abstract IPageable getPageable();

}