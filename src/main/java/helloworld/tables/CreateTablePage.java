package helloworld.tables;

import helloworld.BaseWebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class CreateTablePage extends BaseWebPage {

    public CreateTablePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new EditTable("editTable"));
    }

}
