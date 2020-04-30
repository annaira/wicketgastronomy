package helloworld.tables;

import helloworld.BaseWebPage;
import helloworld.EntityModel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.services.ServiceRegistry;
import helloworld.services.TableService;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Arrays;

public class CreateTablePage extends BaseWebPage {

    private final Form<Table> form = new Form<Table>("form") {

        @Override
        protected void onSubmit() {
            super.onSubmit();
            ServiceRegistry.get(TableService.class).save(form.getModelObject());
            setResponsePage(TablesPage.class);
        }
    };

    public CreateTablePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        initializeForm();
    }

    private void initializeForm() {
        add(form);
        add(new ValidationErrorFeedbackPanel("validationFeedback"));
        form.setModel(new CompoundPropertyModel<>(new EntityModel<>(TableService.class)));
        form.setModelObject(new Table());
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