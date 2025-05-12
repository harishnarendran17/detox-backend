package com.detox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.detox.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findbyUser(String username);

}
