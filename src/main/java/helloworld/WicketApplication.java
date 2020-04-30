package helloworld;

import helloworld.articles.ArticlesPage;
import helloworld.articles.CreateArticlePage;
import helloworld.articles.ModifyArticlePage;
import helloworld.categories.CategoriesPage;
import helloworld.categories.CreateCategoryPage;
import helloworld.categories.ModifyCategoryPage;
import helloworld.converter.BooleanConverter;
import helloworld.converter.CurrencyConverter;
import helloworld.converter.LocalDateConverter;
import helloworld.tables.CreateTablePage;
import helloworld.tables.ModifyTablePage;
import helloworld.tables.TablesPage;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 * @see helloworld.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        new BeanValidationConfiguration().configure(this);

        mountPage("/articles", ArticlesPage.class);
        mountPage("/categories", CategoriesPage.class);
        mountPage("/tables", TablesPage.class);

        mountPage("/article/${id}", ModifyArticlePage.class);
        mountPage("/article/new", CreateArticlePage.class);

        mountPage("/category/${id}", ModifyCategoryPage.class);
        mountPage("/category/new", CreateCategoryPage.class);

        mountPage("/table/${id}", ModifyTablePage.class);
        mountPage("/table/new", CreateTablePage.class);
    }

    @Override
    protected IConverterLocator newConverterLocator() {
        final ConverterLocator defaultConverterLocator = new ConverterLocator();
        defaultConverterLocator.set(Boolean.class, new BooleanConverter());
        defaultConverterLocator.set(LocalDate.class, new LocalDateConverter());
        defaultConverterLocator.set(BigDecimal.class, new CurrencyConverter());
        return defaultConverterLocator;
    }

}
