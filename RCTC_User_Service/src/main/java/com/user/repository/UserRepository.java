package com.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.modal.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	
}
