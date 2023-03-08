package com.stuhome.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stuhome.app.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{
	
	List<User> findByEmailAndPassword(String email, String password);
	List<User> findByEmail(String email);
	
	
}
