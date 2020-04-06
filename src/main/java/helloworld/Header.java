package helloworld;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public class Header extends Panel {
    public Header(String id) {
        super(id);
        add(new Link<Void>("dashboard") {

            @Override
            public void onClick() {
                Header.this.setResponsePage(getApplication().getHomePage());
            }
        });
        add(new Link<Void>("categories") {

            @Override
            public void onClick() {
                setResponsePage(CategoriesPage.class);
            }
        });
        add(new Link<Void>("articles") {

            @Override
            public void onClick() {
                setResponsePage(ArticlesPage.class);
            }
        });
        add(new Link<Void>("tables") {

            @Override
            public void onClick() {
                setResponsePage(TablesPage.class);
            }
        });
    }
}
