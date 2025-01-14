package com.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dtos.UserResponseDto;
import com.user.modal.User;
import com.user.service.UserService;

@RestController
@RequestMapping("/api/user")


public class UserController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable int id) {
    	UserResponseDto user = userService.getUserById(id);
           if(user!=null) {
        	   return ResponseEntity.status(HttpStatus.OK).body(user);
           }
           else {
        	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user);
           }
    	
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createEmployee(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable int id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    	
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            String res=userService.deleteUser(id);
           return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    	
    }
	
}





