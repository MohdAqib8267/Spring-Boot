package com.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.user.dtos.UserResponseDto;
import com.user.modal.User;

@Service
public interface UserService {
	public List<UserResponseDto> getAllUsers();
	public UserResponseDto getUserById(int id);
	public UserResponseDto createUser(User user);
	public User updateUser(int id, User userDetails);
	public String deleteUser(int id);
}
