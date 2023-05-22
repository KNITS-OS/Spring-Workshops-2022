package com.knits.product.mapper;

import com.knits.product.dto.TeamDto;
import com.knits.product.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface TeamMapper extends EntityMapper<Team, TeamDto> {

    @Override
    @Mapping(target = "users", ignore = true)
    TeamDto toDto(Team entity);
}
