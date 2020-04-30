package helloworld.tables;

import helloworld.BaseEntitiesPage;
import helloworld.EntityModel;
import helloworld.SuccessFeedbackPanel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.services.ServiceRegistry;
import helloworld.services.TableService;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Arrays;

public class TablesPage extends BaseEntitiesPage {

    private final DataView<Table> tables;

    private final Form<Table> form = new Form<Table>("form") {

        @Override
        protected void onSubmit() {
            super.onSubmit();
            form.setVisible(false);
            ServiceRegistry.get(TableService.class).save(form.getModelObject());
            form.success("Tisch wurde gespeichert");
        }
    };


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
        add(new Link<String>("newTable") {

            @Override
            public void onClick() {
                form.setVisible(true);
                form.setModelObject(new Table());
            }
        });
        initializeForm();
    }

    private void initializeForm() {
        form.setVisible(false);
        add(form);
        add(new ValidationErrorFeedbackPanel("validationFeedback"));
        add(new SuccessFeedbackPanel("successFeedback"));
        form.setModel(new CompoundPropertyModel<>(new EntityModel<>(TableService.class)));
        form.add(new TextField<String>("name").add(new PropertyValidator<>()));
        form.add(new CheckBox("orderableElectronically"));
        addSeatCountChoiceToForm();
    }

    private void addSeatCountChoiceToForm() {
        final RadioGroup<Integer> radioGroup = new RadioGroup<>("seatCount");
        radioGroup.setRenderBodyOnly(false);
        final ListView<Integer> choices = new ListView<Integer>("choices", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)) {
            @Override
            protected void populateItem(ListItem<Integer> item) {
                item.add(new Label("seatCountLabel", item.getModelObject()));
                item.add(new Radio<>("seatCountValue", item.getModel(), radioGroup));
            }
        };
        form.add(radioGroup);
        radioGroup.add(choices);
    }

}
