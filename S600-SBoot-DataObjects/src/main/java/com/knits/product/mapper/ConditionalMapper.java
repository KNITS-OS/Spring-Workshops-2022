package com.knits.product.mapper;

import org.hibernate.Hibernate;
import org.mapstruct.Condition;
import org.mapstruct.Named;

public class ConditionalMapper {

    @Condition
    @Named("isJpaInitialized")
    public static  boolean isJpaInitialized(Object proxy) {
        if (proxy == null) {
            return false;
        }
        return Hibernate.isInitialized(proxy);
    }
}
