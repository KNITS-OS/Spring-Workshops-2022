package com.knits.product.dto;

public class Mocks {

    public static UserDto mockUser (Long id){
        UserDto userFound = new UserDto();
        userFound.setId(id);
        userFound.setFirstName("Stefano");
        userFound.setLastName("Fiorenza");
        userFound.setActive(true);
        userFound.setLogin("stefano.fiorenza");
        userFound.setPassword(null);
        userFound.setEmail("stefano.fiorenza@email.it");
        return userFound;
    }

}
