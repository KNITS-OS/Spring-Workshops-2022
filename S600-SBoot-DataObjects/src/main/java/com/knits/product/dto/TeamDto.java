package com.knits.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@JsonView(Views.Public.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamDto {

    private Long id;
    private String name;
    private String description;
    private Boolean active = false;

    @JsonView(Views.TeamDetails.class)
    private List<UserDto> users;

    @JsonView(Views.TeamDetails.class)
    private List<Long> userIds;
}
