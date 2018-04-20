package com.pattern.spring.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pattern.spring.model.UserApp;

public interface UserRepository extends PagingAndSortingRepository<UserApp, Long> {

	UserApp findByUserLogin(String userLogin);

}
