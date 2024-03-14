package com.microservice.paymentmethod.helper.enumvalidator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

/**
 * generic enum validator class that validates provided
 * enum and their value. This enum validator is used
 * as validation constraint within payload request
 *
 * @author Asif Bakht
 * @since 2024
 */
public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    List<String> enums = null;

    /**
     * verify the enum value provided in payload is valid
     *
     * @param enumValue enum value to validate
     * @param context   context in which the constraint is evaluated
     * @return {@link Boolean} indicates enum value is valid
     */
    @Override
    public boolean isValid(final String enumValue, final ConstraintValidatorContext context) {
        return enums.contains(enumValue.toUpperCase());
    }

    /**
     * setup enum values of the class provided
     *
     * @param constraint annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(final EnumValidator constraint) {
        Class<? extends Enum<?>> enumClass = constraint.enumClazz();
        enums = Stream.of(enumClass.getEnumConstants()).map(Enum::toString).toList();
    }

}
