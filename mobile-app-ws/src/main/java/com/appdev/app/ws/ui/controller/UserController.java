package com.appdev.app.ws.ui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdev.app.ws.ui.model.request.UpdateUserDetailrequestModel;
import com.appdev.app.ws.ui.model.request.UserDetailRequestModel;
import com.appdev.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {
	
	Map<String, UserRest> userMap;
	
	@GetMapping
	public String getUsers(@RequestParam(value = "page") int page, 
			@RequestParam(value = "limit") int limit) {
		return "get users called from page " +page+" to page "+limit;
	}
	
	@GetMapping(path = "/{userId}", 
			produces = {MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE})
	public UserRest getUser(@PathVariable String userId) {
		
		if(userMap.containsKey(userId)) {
			return userMap.get(userId);
		}else {
			return null;
		}
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE})
	public UserRest createUser(@Valid @RequestBody UserDetailRequestModel userDetails) {
	
		UserRest user = new UserRest();
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		
		String uuid = UUID.randomUUID().toString();
		user.setUserId(uuid);
		if(userMap == null) userMap = new HashMap<>();
		userMap.put(uuid,user);
		return user;
	}
	
	@PutMapping(path = "/{userId}",consumes = {MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE})
	public UserRest updateUser(@PathVariable String userId, @Valid @RequestBody 
			UpdateUserDetailrequestModel updatedUserDetails	) {
		
		UserRest user = userMap.get(userId);
		user.setFirstName(updatedUserDetails.getFirstName());
		user.setLastName(updatedUserDetails.getLastName());
		userMap.put(userId, user);
		return user;
	}
	
	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		userMap.remove(userId);
		return ResponseEntity.noContent().build();
	}
}
