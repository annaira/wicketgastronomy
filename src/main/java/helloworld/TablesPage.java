package helloworld;

import helloworld.entities.Table;
import helloworld.services.ServiceRegistry;
import helloworld.services.TableService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

public class TablesPage extends BaseEntitiesPage {
    public TablesPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final List<Table> tableList = new ArrayList<>(ServiceRegistry.get(TableService.class).listAll());

        final IDataProvider<Table> dataProvider = new ListDataProvider<>(tableList);

        final DataView<Table> tables = new DataView<Table>("tables", dataProvider) {
            @Override
            protected void populateItem(Item<Table> item) {
                item.add(new Label("name", item.getModelObject().getName()));
                item.add(new Label("seatCount", item.getModelObject().getSeatCount()));
                item.add(new Label("orderableElectronically", item.getModelObject().getFormattedOrderableElectronically()));

            }
        };
        tables.setItemsPerPage(3);
        final PagingNavigation navigation = new PagingNavigation("navigation", tables);
        add(tables);
        add(navigation);
    }

}
