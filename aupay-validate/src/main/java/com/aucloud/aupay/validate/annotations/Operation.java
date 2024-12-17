package com.aucloud.aupay.validate.annotations;

import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Operation {

    @AliasFor("permission")
    String value() default "";

    @AliasFor("value")
    String permission() default "";

    OperationEnum operation();

    VerifyMethod[] verifyMethods() default {};

}
