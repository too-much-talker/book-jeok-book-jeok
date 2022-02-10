package com.ssafy.bjbj.common.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import static com.ssafy.bjbj.common.validator.EnumListValidator.*;

@Constraint(validatedBy = {EnumListValidatorImpl.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumListValidator {

    String message() default "올바르지 않은 요청입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    boolean ignoreCase() default false;

    class EnumListValidatorImpl implements ConstraintValidator<EnumListValidator, List<String>> {
        private EnumListValidator annotation;

        @Override
        public void initialize(EnumListValidator constraintAnnotation) {
            this.annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(List<String> values, ConstraintValidatorContext context) {
            Object[] enumValues = this.annotation.enumClass().getEnumConstants();
            if (enumValues != null) {
                for (String value : values) {
                    boolean valid = false;
                    for (Object enumValue : enumValues) {
                        if (value.equals(enumValue.toString())
                                || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                            valid = true;
                        }
                    }
                    if (!valid) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

}
