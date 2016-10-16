package com.tenx.ms.retail.common.util;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to simply indicate a Field access is allowed for Entity conversion.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowConverterAccess {
}
