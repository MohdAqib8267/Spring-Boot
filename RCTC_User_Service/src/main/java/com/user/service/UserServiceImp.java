package com.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.dtos.UserRequestDto;
import com.user.dtos.UserResponseDto;
import com.user.modal.User;
import com.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Service
public class UserServiceImp implements UserService {
	
	@Autowired
	private UserRepository repo;
	
	public UserResponseDto createUser(@RequestBody User user) {
		User savedUser = repo.save(user);
		UserResponseDto res = new UserResponseDto(savedUser.getId(),savedUser.getUsername(),savedUser.getEmail());
//		return ResponseEntity.status(HttpStatus.CREATED).body(res);
		return res;
	}
	
	public List<UserResponseDto> getAllUsers(){
		List<User> users = repo.findAll();
		List<UserResponseDto> res = new ArrayList<>();
		for(User u:users) {
			res.add(new UserResponseDto(u.getId(),u.getUsername(),u.getPassword()));
		}
//		return ResponseEntity.status(HttpStatus.OK).body(res);
		return res;
	}
	public UserResponseDto getUserById(@RequestParam int id) {
	    Optional<User> optionalUser = repo.findById(id);

	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get(); 
	        UserResponseDto res = new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());
//	        return ResponseEntity.status(HttpStatus.OK).body(res);
	        return res;
	    } else {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    	return null;
	    }
	}

	public User updateUser(@RequestParam int id, @RequestBody User userDetails) {
	    Optional<User> optionalUser = repo.findById(id);

	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();

	        user.setUsername(userDetails.getUsername());
	        user.setPassword(userDetails.getPassword());

	        User updatedUser = repo.save(user);

//	        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	        return updatedUser;
	    } else {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    	return null;
	    }
	}

	public String deleteUser(@RequestParam int id){
		Optional<User> optionalUser = repo.findById(id);
		if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        repo.delete(user);

//	        return ResponseEntity.status(HttpStatus.OK).body("User Deleted Successfully");
	        return "User Deleted Successfully";
	    } else {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    	return "Not Found";
	    }
	}
	
	
}
