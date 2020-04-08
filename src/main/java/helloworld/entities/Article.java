package helloworld.entities;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Currency.getInstance;
import static java.util.Locale.GERMANY;

public class Article extends BaseEntity {

    private Category category;

    private LocalDate validFrom = LocalDate.now();

    private LocalDate validTo = LocalDate.MAX;

    private String name;

    private String imageUrl;

    private BigDecimal price;

    private String description;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", GERMANY);

    public Article(Category category, String name, String imageUrl, BigDecimal price, String description) {
        this.category = category;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.setValidFrom(LocalDate.of(2018, 1, 1));
        this.setValidTo(LocalDate.MAX);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public String getFormattedValidFrom() {
        return validFrom.format(dateTimeFormatter);
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public String getFormattedValidTo() {
        if (validTo.equals(LocalDate.MAX)) {
            return "-";
        }
        return validTo.format(dateTimeFormatter);
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        NumberFormat format = NumberFormat.getCurrencyInstance(GERMANY);
        format.setCurrency(getInstance("EUR"));
        return format.format(price);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
