package helloworld;

import helloworld.entities.Category;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class CategoriesPage extends BaseEntitiesPage {
    public CategoriesPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final RepeatingView categories = new RepeatingView("categories");
        for (Category category : ServiceRegistry.get(CategoryService.class).listAll()) {
            categories.add(new Label(categories.newChildId(), category.getName()));
        }
        add(categories);
    }
}
