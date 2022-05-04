package com.knits.spring.common.repositories;

import java.util.List;

import com.knits.spring.common.model.User;

public interface UserRepository {

	Long persist(User user);
	
	List<User> searchUsersByCity(String city);
	
	void merge (User user);
}
