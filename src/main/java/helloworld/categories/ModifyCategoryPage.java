package helloworld.categories;

import helloworld.BaseWebPage;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ModifyCategoryPage extends BaseWebPage {

    public ModifyCategoryPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final Long categoryId = getPageParameters().get("id").to(Long.class);
        final EditCategory editCategory = new EditCategory("editCategory");
        editCategory.setCategory(ServiceRegistry.get(CategoryService.class).get(categoryId));
        add(editCategory);
    }

}
