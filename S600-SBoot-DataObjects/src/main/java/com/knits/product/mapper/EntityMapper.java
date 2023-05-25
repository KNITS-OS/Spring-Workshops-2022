package com.knits.product.mapper;

import org.hibernate.Hibernate;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

public interface EntityMapper<E, D> {

    E toEntity(D dto);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<E> toEntityList(List<D> entityList);
    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    Set<E> toEntitySet(Set<D> entityList);

    D toDto(E entity);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    List<D> toDtoList(List<E> entityList);
    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    Set<D> toDtoSet(Set<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);

    @Named("update")
    void update(@MappingTarget E entity,  D dto);


}