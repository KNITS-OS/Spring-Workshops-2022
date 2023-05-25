package com.knits.product.mapper;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.mapstruct.Condition;
import org.mapstruct.Named;

import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

@Slf4j
public class JpaMapperUtils {

    @Condition
    @Named("isJpaInitialized")
    public static  boolean isJpaInitialized(Object proxy) {
        if (proxy == null) {
            return false;
        }
        log.info ("P Initialized in MapperUtils? {} ",Persistence.getPersistenceUtil().isLoaded(proxy));
        log.info ("Initialized in MapperUtils? {} ",Hibernate.isInitialized(proxy));
        return Hibernate.isInitialized(proxy);
    }

//    @Condition
//    @Named("isJpaListInitialized")
//     public static  boolean isJpaListInitialized(Iterable listOfEntities) {
//        int count =0;
//
//        if (listOfEntities == null) {
//            return false;
//        }
//        log.info ("P Initialized in MapperUtils? {} ",Persistence.getPersistenceUtil().isLoaded(listOfEntities));
//
//        for (Object o:listOfEntities) {
//            count++;
//            if (!isJpaInitialized(o))
//                return false;
//        }
//        if (count==0){
//            return false;
//        }
//        return true;
//    }
}
