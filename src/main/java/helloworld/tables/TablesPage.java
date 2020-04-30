package helloworld.tables;

import helloworld.BaseEntitiesPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class TablesPage extends BaseEntitiesPage {

    private final DataView<Table> tables;

    public TablesPage(PageParameters parameters) {
        super(parameters);
        final IDataProvider<Table> dataProvider = new TablesDataProvider();

        tables = new DataView<Table>("tables", dataProvider) {
            @Override
            protected void populateItem(Item<Table> item) {
                item.setModel(new CompoundPropertyModel<>(item.getModel()));
                item.add(new Label("name"));
                item.add(new Label("seatCount"));
                item.add(new Label("orderableElectronically"));
                item.add(new BookmarkablePageLink<>("modifyTable", ModifyTablePage.class, new PageParameters().add("id", item.getModelObject().getId())));
            }
        };
    }

    @Override
    protected IPageable getPageable() {
        return tables;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(tables);
    }

}
