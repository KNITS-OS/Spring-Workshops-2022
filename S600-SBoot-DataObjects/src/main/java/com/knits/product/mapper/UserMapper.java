package com.knits.product.mapper;

import com.knits.product.model.User;
import com.knits.product.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface UserMapper extends EntityMapper<User, UserDto> {

    @Override
   // @Mapping(target = "groups", ignore = true)
    UserDto toDto(User entity);

    @Override
   // @Mapping(target = "groups", ignore = true)
    User toEntity(UserDto entity);

}
