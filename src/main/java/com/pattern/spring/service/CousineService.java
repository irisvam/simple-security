package com.pattern.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pattern.spring.model.Person;
import com.pattern.spring.repositories.PersonRepository;

@Service
public class CousineService {

	@Autowired
	private PersonRepository repCousine;

	public Person save(final Person cousine) {

		return repCousine.save(cousine);
	}

	public List<Person> listAll() {

		return (List<Person>) repCousine.findAll();
	}

	public Person findById(final Long id) {

		return repCousine.findOne(id);
	}

	public List<Person> findByName(final String name) {

		return repCousine.findByNameContainingIgnoreCase(name);
	}

	public boolean isCousineExist(final Person cousine) {

		List<Person> lista = repCousine.findByNameAndSurename(cousine.getName(), cousine.getSurename());

		if (null == lista || lista.isEmpty()) {

			return false;
		}

		return true;
	}

	public void deleteById(Long cousineId) {

		repCousine.delete(cousineId);
	}

	public void update(Person cousine) {

		repCousine.save(cousine);
	}

}
