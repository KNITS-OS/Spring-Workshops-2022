package com.knits.product.mocks.dto;

import com.knits.product.service.dto.UserDto;

public class UserDtoMocks {

    public static UserDto shallowUserDto(Long id){

        return UserDto.builder()
                .id(id)
                .firstName("A mock firstName")
                .lastName("A mock lastName")
                .login("A mock username")
                .password("A mock password")
                .email("aMockName.aMockSurname@aMockDomain.com")
                .build();
    }
}
