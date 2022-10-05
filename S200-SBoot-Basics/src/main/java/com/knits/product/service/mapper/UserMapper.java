package com.knits.product.service.mapper;

import com.knits.product.model.User;
import com.knits.product.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper extends EntityMapper<User, UserDto> {



}
