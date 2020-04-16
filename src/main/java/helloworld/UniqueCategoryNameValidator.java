package helloworld;

import helloworld.entities.Category;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;

public class UniqueCategoryNameValidator implements org.apache.wicket.validation.IValidator<String> {
    @Override
    public void validate(IValidatable<String> validatable) {
        final String givenCategoryName = validatable.getValue();
        final Category existingCategoryWithSameName = ServiceRegistry.get(CategoryService.class).getByName(givenCategoryName);
        if (existingCategoryWithSameName != null) {
            final ValidationError error = new ValidationError(this);
            error.setVariable("suggestedCategory", "Weine");
            validatable.error(error);
        }
    }
}
