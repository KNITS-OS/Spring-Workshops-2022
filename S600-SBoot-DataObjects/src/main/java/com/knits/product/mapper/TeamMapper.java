package com.knits.product.mapper;

import com.knits.product.dto.TeamDto;
import com.knits.product.model.Team;
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses={UserMapper.class, ConditionalMapper.class}
)
public interface TeamMapper extends EntityMapper<Team, TeamDto>{


    @Override
    @Mapping(target = "users", ignore = true)
    TeamDto toDto(Team entity);
    @Named("toDtoDetails")
    @Mapping(source="users" ,target = "users")
    TeamDto toDtoDetails(Team entity);
    @Named("toDtoConditional")
    @Mapping(source="users" ,target = "users", conditionQualifiedByName = "isJpaInitialized")
    TeamDto toDtoConditional(Team entity);

    @Override
    default List<TeamDto> toDtoList(List<Team> entityList){
        if ( entityList == null ) {
            return new ArrayList<TeamDto>();
        }
        List<TeamDto> list = new ArrayList<TeamDto>( entityList.size() );
        for ( Team team : entityList ) {
            list.add( toDtoDetails( team ) );
        }
        return list;
    }

    @Override
    @Mapping(target = "users", ignore = true)
    Team toEntity(TeamDto dto);

}

