package org.example.store.service.validation;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = BigDecimalRangeValidator.class)
@Documented
public @interface BigDecimalRange {
    public String message() default "{java.math.BigDecimal.range.error}";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};

    long minPrecision() default Long.MIN_VALUE;
    long maxPrecision() default Long.MAX_VALUE;
    int scale() default 0;
}