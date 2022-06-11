package com.github.ewanme.romresbot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.ewanme.romresbot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
}
