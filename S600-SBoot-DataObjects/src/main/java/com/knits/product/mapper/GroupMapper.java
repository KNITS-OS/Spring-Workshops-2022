package com.knits.product.mapper;

import com.knits.product.dto.GroupDto;
import com.knits.product.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface GroupMapper extends EntityMapper<Group, GroupDto>{
}
