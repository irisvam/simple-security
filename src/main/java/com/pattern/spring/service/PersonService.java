package com.pattern.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pattern.spring.model.Person;
import com.pattern.spring.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repPerson;

	public Person save(final Person cousine) {

		return repPerson.save(cousine);
	}

	public List<Person> listAll() {

		return (List<Person>) repPerson.findAll();
	}

	public Person findById(final Long id) {

		Optional<Person> person = repPerson.findById(id);

		return person.get();
	}

	public List<Person> findByName(final String name) {

		return repPerson.findByNameContainingIgnoreCase(name);
	}

	public boolean isPersonExist(final Person person) {

		List<Person> lista = repPerson.findByNameAndSurename(person.getName(), person.getSurename());

		if (null == lista || lista.isEmpty()) {

			return false;
		}

		return true;
	}

	public void deleteById(Long personId) {

		repPerson.deleteById(personId);
	}

	public void update(Person person) {

		repPerson.save(person);
	}

}
