package com.crasleepish.springbootbackend.util.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface ExistClassInfo {
    String message() default "不存在该班级";

    Class<?>[] groups() default { };

    Class<? extends Payload[]>[] payload() default { };
}
