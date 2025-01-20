package com.capstone.CaseStudy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;


public class TwoFieldsAreEqualImpl implements ConstraintValidator<TwoFieldsAreEqual, Object> {

    private String fieldOneName;
    private String fieldTwoName;

    @Override
    public void initialize(TwoFieldsAreEqual constraintAnnotation) {
        fieldOneName = constraintAnnotation.fieldOneName();
        fieldTwoName = constraintAnnotation.fieldTwoName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            final String fieldOneValue = getFieldValue(value, fieldOneName);
            final String fieldTwoValue = getFieldValue(value, fieldTwoName);


//             both are null
            if (fieldOneValue == null && fieldTwoValue == null) {
                return true;
            }

            // if they are equal then return true
            if (fieldOneValue.equals(fieldTwoValue)) {
                return true;
            }

        } catch (Exception e) {
            // do nothing
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(fieldOneName)
                .addConstraintViolation();

        return false;
    }

    private String getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        // Get the field from the object's class
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // Allow access to private fields

        // Get the field value and convert it to a String
        Object fieldValue = field.get(object);
        return fieldValue != null ? fieldValue.toString() : null;
    }

}
