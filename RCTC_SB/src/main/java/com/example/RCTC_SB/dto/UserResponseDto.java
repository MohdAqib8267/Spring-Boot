package com.example.RCTC_SB.dto;


public class UserResponseDto {
	private int id;
	private String username;
	private String email;
	
	
	public UserResponseDto(int i, String username, String email) {
		
		this.id = i;
		this.username = username;
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}

