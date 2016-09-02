package com.tenx.ms.retail.common.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to simply indicate a Getter Method access is denied for Entity conversion.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DenyConverterAccess {
}
