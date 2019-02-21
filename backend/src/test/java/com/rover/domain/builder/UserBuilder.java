package com.rover.domain.builder;

import java.util.List;

import com.rover.domain.SitterReview;
import com.rover.domain.User;
import com.rover.domain.repository.UserRepository;

public class UserBuilder {

	private User user;
	private UserRepository userRepository;

	public static UserBuilder build(UserRepository userRepository) {
		return new UserBuilder(userRepository);
	}

	public UserBuilder(UserRepository userRepository) {
		this.userRepository = userRepository;
		user = new User();
	}

	// with methods

	public UserBuilder withName(String name) {
		user.setName(name);
		return this;
	}

	public UserBuilder withEmail(String email) {
		user.setEmail(email);
		return this;
	}

	public UserBuilder withPhoneNumber(String phoneNumber) {
		user.setPhoneNumber(phoneNumber);
		return this;
	}

	public UserBuilder withImageUrl(String imageUrl) {
		user.setImageUrl(imageUrl);
		return this;
	}
	
	// access methods

	public UserBuilder persist() {
		userRepository.save(user);
		return this;
	}

	public User get() {
		return user;
	}
}
