package helloworld.tables;

import helloworld.EntityModel;
import helloworld.services.ServiceRegistry;
import helloworld.services.TableService;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import java.util.Iterator;

public class TablesDataProvider extends SortableDataProvider<Table, Void> {
    @Override
    public Iterator<? extends Table> iterator(long first, long count) {
        return ServiceRegistry.get(TableService.class).listAll().iterator();
    }

    @Override
    public long size() {
        return ServiceRegistry.get(TableService.class).listAll().size();
    }

    @Override
    public IModel<Table> model(Table table) {
        return new EntityModel<>(table, TableService.class);
    }
}
