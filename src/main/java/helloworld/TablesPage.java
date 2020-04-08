package helloworld;

import helloworld.entities.Table;
import helloworld.services.ServiceRegistry;
import helloworld.services.TableService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

public class TablesPage extends BaseEntitiesPage {

    private final DataView<Table> tables;

    public TablesPage(PageParameters parameters) {
        super(parameters);
        final List<Table> tableList = new ArrayList<>(ServiceRegistry.get(TableService.class).listAll());

        final IDataProvider<Table> dataProvider = new ListDataProvider<>(tableList);

        tables = new DataView<Table>("tables", dataProvider) {
            @Override
            protected void populateItem(Item<Table> item) {
                Table table = item.getModelObject();
                item.add(new Label("name", table.getName()));
                item.add(new Label("seatCount", table.getSeatCount()));
                item.add(new Label("orderableElectronically", table.getFormattedOrderableElectronically()));

            }
        };
    }

    @Override
    IPageable getPageable() {
        return tables;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        tables.setItemsPerPage(3);
        add(tables);
    }

}
