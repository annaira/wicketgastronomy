package helloworld.tables;

import helloworld.EntityModel;
import helloworld.ValidationErrorFeedbackPanel;
import helloworld.services.ServiceRegistry;
import helloworld.services.TableService;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import java.util.Arrays;

public class EditTable extends Panel {

    private final Form<Table> form = new Form<Table>("form") {

        @Override
        protected void onSubmit() {
            super.onSubmit();
            ServiceRegistry.get(TableService.class).save(form.getModelObject());
            setResponsePage(TablesPage.class);
        }
    };

    private AjaxCheckBox orderableElectronically;

    private final AttributeModifier dataAttributeModifier;
    private final Component qrCode = new WebMarkupContainer("qrCode") {

        @Override
        protected void onConfigure() {
            super.onConfigure();
            final Boolean modelObject = EditTable.this.orderableElectronically.getModelObject();
            setVisible(modelObject == null ? false : modelObject);
        }
    }.setOutputMarkupPlaceholderTag(true);

    public EditTable(String id) {
        super(id);
        form.setModel(new CompoundPropertyModel<>(new EntityModel<>(TableService.class)));
        this.dataAttributeModifier = new AttributeModifier("data", new Model<String>() {

            @Override
            public String getObject() {
                final Long id = EditTable.this.form.getModelObject().getId();
                return id != null ? String.format("https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http://localhost:8000/table/%d", id) : null;
            }
        });
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(form);
        add(new ValidationErrorFeedbackPanel("validationFeedback"));
        form.add(new TextField<String>("name").add(new PropertyValidator<>()));
        orderableElectronically = new AjaxCheckBox("orderableElectronically") {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                target.add(EditTable.this.qrCode);
            }

        };
        orderableElectronically.add(new PropertyValidator<>());
        form.add(orderableElectronically);
        addSeatCountChoiceToForm();
        this.form.add(this.qrCode);
        this.qrCode.add(dataAttributeModifier);
    }

    private void addSeatCountChoiceToForm() {
        final RadioGroup<Integer> radioGroup = new RadioGroup<>("seatCount");
        radioGroup.setRenderBodyOnly(false);
        final ListView<Integer> choices = new ListView<Integer>("choices", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)) {
            @Override
            protected void populateItem(ListItem<Integer> item) {
                item.add(new Label("seatCountLabel", item.getModelObject()));
                item.add(new Radio<>("seatCountValue", item.getModel(), radioGroup));
            }
        };
        form.add(radioGroup.add(new PropertyValidator<>()));
        radioGroup.add(choices);
    }

    void setTable(Table table) {
        form.setModelObject(table);
    }

}
