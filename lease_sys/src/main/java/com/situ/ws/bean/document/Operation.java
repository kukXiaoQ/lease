package com.situ.ws.bean.document;

import com.situ.ws.bean.enums.OperationStatus;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Operation {

    OperationStatus value();

}
