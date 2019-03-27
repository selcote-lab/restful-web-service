package com.tonasolution.rest.webservices.restfulwebservices.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tonasolution.rest.webservices.restfulwebservices.dao.UserDaoService;
import com.tonasolution.rest.webservices.restfulwebservices.entity.User;
import com.tonasolution.rest.webservices.restfulwebservices.exceptions.UserNotFoundException;

@RestController
public class UserController {
	
	@Autowired
	private UserDaoService userDaoService;
	
	@GetMapping("/users")
	public List<User> retreiveAllUsers(){
		return userDaoService.findAll();
	}
	
	@GetMapping("/users/{id}")
	public Resource<User> retreiveUser(@PathVariable int id) {
		User user = userDaoService.findOne(id);
		if(user==null)
			throw new UserNotFoundException(" id- " + id);
		
		Resource<User> resource = new Resource<User>(user);
		
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
				ControllerLinkBuilder
				.methodOn(this.getClass())
				.retreiveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDaoService.deleteById(id);
		if(user==null)
			throw new UserNotFoundException("id-" + id);
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
								.buildAndExpand(savedUser.getId())
								.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
}