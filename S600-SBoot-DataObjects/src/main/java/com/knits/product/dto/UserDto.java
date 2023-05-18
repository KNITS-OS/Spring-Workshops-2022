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
public class UserDto {

    @EqualsAndHashCode.Include
    private Long id;

    private String login;

    @ToString.Exclude
    private String password;

    @EqualsAndHashCode.Include
    private String firstName;

    @EqualsAndHashCode.Include
    private String lastName;

    private String email;

    private Boolean active = true;

    @JsonView(Views.UserDetails.class)
    private List<GroupDto> groups;
}
