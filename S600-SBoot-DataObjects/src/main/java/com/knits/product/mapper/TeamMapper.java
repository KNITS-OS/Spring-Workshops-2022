package com.knits.product.mapper;

import com.knits.product.dto.TeamDto;
import com.knits.product.model.Team;
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses={UserMapper.class, JpaMapperUtils.class}
)
public interface TeamMapper extends EntityMapper<Team, TeamDto>{

    @Override
    @Mapping(source="users" ,target = "users", conditionQualifiedByName = "isJpaInitialized")
    TeamDto toDto(Team entity);


    @Override
    //@Mapping(target = "users", ignore = true)
    Team toEntity(TeamDto dto);





}

