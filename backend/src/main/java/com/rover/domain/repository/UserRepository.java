package com.rover.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rover.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
