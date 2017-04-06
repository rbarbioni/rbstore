package br.com.rbarbioni.bluebank.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by renan on 11/02/17.
 */

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
@Constraint(validatedBy = {CpfValidator.class})
public @interface Cpf {

    Class<?>[] groups() default {};
    String message() default "{br.com.rbarbioni.bluebank.util.cpf}";
    Class<? extends Payload>[] payload() default {};
}