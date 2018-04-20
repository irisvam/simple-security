package com.pattern.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pattern.spring.domain.Message;
import com.pattern.spring.model.Person;
import com.pattern.spring.service.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	PersonService service;

	@RequestMapping("/")
	public String init(@AuthenticationPrincipal final UserDetails userDetails) {

		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping("/hello/{player}")
	public Message mensage(@PathVariable String player) {

		Message msg = new Message(player, "Hello " + player);

		return msg;
	}

	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	public ResponseEntity<Person> findById(@PathVariable Long personId) {

		Person current = service.findById(personId);

		if (current == null) {

			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Person>(current, HttpStatus.OK);
	}

	@RequestMapping(value = "/{personId}", method = RequestMethod.DELETE)
	public ResponseEntity<Person> delete(@PathVariable Long personId) {

		Person current = service.findById(personId);

		if (current == null) {

			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}

		service.deleteById(personId);

		return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{personId}", method = RequestMethod.PUT)
	public ResponseEntity<Person> updateUser(@PathVariable Long personId, @RequestBody Person person) {

		Person current = service.findById(personId);

		if (current == null) {

			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}

		current.setName(person.getName());

		service.update(current);

		return new ResponseEntity<Person>(current, HttpStatus.OK);
	}

	@RequestMapping(value = "/search/{searchText}", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> findByName(@PathVariable String searchText) {

		List<Person> list = service.findByName(searchText);

		if (list.isEmpty()) {

			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Person>> listAll() {

		List<Person> list = service.listAll();

		if (list.isEmpty()) {

			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Person person, UriComponentsBuilder ucBuilder) {

		if (service.isPersonExist(person)) {

			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		service.save(person);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/{personId}").buildAndExpand(person.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

}
