package helloworld;

import helloworld.entities.Category;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryDataProvider extends SortableDataProvider<Category, String> {

    public CategoryDataProvider() {
        setSort("name", SortOrder.ASCENDING);
    }

    @Override
    public Iterator<? extends Category> iterator(long first, long count) {
        List<Category> allCategories = new ArrayList<>(ServiceRegistry.get(CategoryService.class).listAll());

        final SortParam<String> sortOrder = getSort();
        allCategories.sort((category1, category2) -> {
            String name1 = category1.getName();
            String name2 = category2.getName();
            return sortOrder.isAscending() ? name1.compareTo(name2) : name2.compareTo(name1);
        });

        List<Category> subList = allCategories.subList((int) first, (int) (first + count >= allCategories.size() ? allCategories.size() : first + count));
        return subList.iterator();
    }

    @Override
    public long size() {
        return ServiceRegistry.get(CategoryService.class).listAll().size();
    }

    @Override
    public IModel<Category> model(Category object) {
        return Model.of(object);
    }
}
