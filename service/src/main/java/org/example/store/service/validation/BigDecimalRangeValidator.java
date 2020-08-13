package org.example.store.service.validation;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class BigDecimalRangeValidator implements ConstraintValidator<BigDecimalRange, Object> {

    private long maxPrecision;
    private long minPrecision;
    private int scale;

    @Override
    public void initialize(final BigDecimalRange bigDecimalRange) {
        maxPrecision = bigDecimalRange.maxPrecision();
        minPrecision = bigDecimalRange.minPrecision();
        scale = bigDecimalRange.scale();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext cvc) {
        boolean isValid = false;

        if (object == null) { // This should be validated by the not null validator (@NotNull).
            isValid = true;
        } else if (object instanceof BigDecimal) {
            BigDecimal bigDecimal = new BigDecimal(object.toString());
            int actualPrecision = bigDecimal.precision();
            int actualScale = bigDecimal.scale();
            isValid = actualPrecision >= minPrecision && actualPrecision <= maxPrecision && actualScale <= scale;

            if (!isValid) {
                cvc.disableDefaultConstraintViolation();
                cvc.buildConstraintViolationWithTemplate("Precision expected (minimum : "
                        + minPrecision + ", maximum : " + maxPrecision + "). Maximum scale expected : "
                        + scale + ". Found precision : " + actualPrecision + ", scale : "
                        + actualScale).addConstraintViolation();
            }
        }

        return isValid;
    }
}