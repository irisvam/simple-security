package com.pattern.spring.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pattern.spring.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findByNameAndSurename(String name, String surename);

	List<Person> findByNameContainingIgnoreCase(String name);

}
