package com.knits.product.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.knits.product.dto.views.Views;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@JsonView(Views.Public.class)
public class TeamDto {

    private Long id;
    private String name;
    private String description;
    private Boolean active = false;

    @JsonView(Views.TeamDetails.class)
    private List<UserDto> users;

}
