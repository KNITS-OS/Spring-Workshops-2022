package com.knits.product.mapper;

import com.knits.product.dto.TeamDto;
import com.knits.product.dto.UserDto;
import com.knits.product.model.Team;
import com.knits.product.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper extends EntityMapper<User, UserDto>{
}

