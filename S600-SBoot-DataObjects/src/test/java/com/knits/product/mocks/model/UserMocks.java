package com.knits.product.mocks.model;

import com.knits.product.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMocks {

    public static User shallowUser(Long id){

        return User.builder()
                .id(id)
                .firstName("A mock firstName")
                .lastName("A mock lastName")
                .login("A mock username")
                .password("A mock password")
                .email("aMockName.aMockSurname@aMockDomain.com")
                .build();
    }

    public static List<User> shallowListOfUsers(int howMany){
        List<User> mockUsers = new ArrayList<>();
        for (int i=0;i<howMany; i++){
            mockUsers.add(shallowUser(Long.valueOf(i)));
        }
        return mockUsers;
    }
}

