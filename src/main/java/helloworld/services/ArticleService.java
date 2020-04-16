package helloworld.services;

import helloworld.articles.Article;

import java.math.BigDecimal;

public class ArticleService extends BaseService<Article> {
    public ArticleService() {
        final CategoryService categoryService = ServiceRegistry.get(CategoryService.class);

        final Article cappuccino = new Article(categoryService.get(1L), "Cappuccino", "https://images.freeimages.com/images/large-previews/a79/cappuccino-1497220.jpg",
                new BigDecimal("3.20"), "Italienische Kaffeespezialität, die aus einem Espresso und heißem Milchschaum zubereitet wird.");

        save(cappuccino);
    }
}
