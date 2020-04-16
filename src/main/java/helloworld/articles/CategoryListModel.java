package helloworld.articles;

import helloworld.categories.Category;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryListModel implements IModel<List<Category>> {
    @Override
    public List<Category> getObject() {
        return new ArrayList<>(ServiceRegistry.get(CategoryService.class).listAll());
    }
}
