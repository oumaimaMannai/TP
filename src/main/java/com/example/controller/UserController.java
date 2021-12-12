package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
		
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> get(@PathVariable Long id){
		User user = userRepository.findById(id).orElseThrow(null);			
		if (user != null) {
	        UserDto userDto = new UserDto();
	        BeanUtils.copyProperties(user, userDto);
	        return ResponseEntity.ok(userDto);
	    }
	    return ResponseEntity.notFound().build();
	}
		
	@PostMapping("/add")
	public ResponseEntity<UserDto> add(@RequestBody User user) {
		userRepository.save(user);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return ResponseEntity.ok(userDto);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userRepository.deleteById(id);
	}	
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> update(@PathVariable("id") Long Id,@RequestBody User user) {	          
		User u = userRepository.findById(Id).orElseThrow(null);			
		u.setEmail(user.getEmail());
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		userRepository.save(u);
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return ResponseEntity.ok(userDto);
	}
}