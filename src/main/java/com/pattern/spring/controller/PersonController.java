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
import com.pattern.spring.service.CousineService;

@RestController
public class PersonController {

	@Autowired
	CousineService service;

	@RequestMapping("/")
	public String init(@AuthenticationPrincipal final UserDetails userDetails) {

		return "Welcome to RestTemplate Example.";
	}

	@RequestMapping("/hello/{player}")
	public Message mensage(@PathVariable String player) {

		Message msg = new Message(player, "Hello " + player);

		return msg;
	}

	@RequestMapping(value = "/cousine/{cousineId}", method = RequestMethod.GET)
	public ResponseEntity<Person> findById(@PathVariable Long cousineId) {

		Person cousine = service.findById(cousineId);

		if (cousine == null) {

			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Person>(cousine, HttpStatus.OK);
	}

	@RequestMapping(value = "/cousine/{cousineId}", method = RequestMethod.DELETE)
	public ResponseEntity<Person> delete(@PathVariable Long cousineId) {

		Person cousine = service.findById(cousineId);

		if (cousine == null) {

			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		
		service.deleteById(cousineId);

		return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/cousine/{cousineId}", method = RequestMethod.PUT)
    public ResponseEntity<Person> updateUser(@PathVariable Long cousineId, @RequestBody Person cousine) {
         
		Person currentCousine = service.findById(cousineId);
         
        if (currentCousine==null) {
        	
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
 
        currentCousine.setName(cousine.getName());
         
        service.update(currentCousine);
        
        return new ResponseEntity<Person>(currentCousine, HttpStatus.OK);
    }

	@RequestMapping(value = "/cousine/search/{searchText}", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> findByName(@PathVariable String searchText) {

		List<Person> list = service.findByName(searchText);

		if (list.isEmpty()) {

			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/cousine/", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> listAll() {

		List<Person> list = service.listAll();

		if (list.isEmpty()) {

			return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Person>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/cousine/", method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Person cousine, UriComponentsBuilder ucBuilder) {

		if (service.isCousineExist(cousine)) {

			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		service.save(cousine);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/cousine/{cousineId}").buildAndExpand(cousine.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

}
