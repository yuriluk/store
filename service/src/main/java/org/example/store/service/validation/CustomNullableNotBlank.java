package org.example.store.service.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = CustomNullableNotBlankValidator.class)
@Target({METHOD, FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomNullableNotBlank {

    String message() default "Can`t be spaces, sorry!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
