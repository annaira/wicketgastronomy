package helloworld;

import helloworld.entities.Category;
import helloworld.services.CategoryService;
import helloworld.services.ServiceRegistry;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryNameValidator implements ConstraintValidator<UniqueCategoryName, String> {

    @Override
    public void initialize(UniqueCategoryName constraintAnnotation) {

    }

    @Override
    public boolean isValid(String givenCategoryName, ConstraintValidatorContext constraintValidatorContext) {
        for (Category category : ServiceRegistry.get(CategoryService.class).listAll()) {
            if (category.getName().equals(givenCategoryName)) {
                return false;
            }
        }
        return true;
    }

}