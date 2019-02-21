package com.rover;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rover.api.rest.v1.endpoint.SitterEndpoints;
import com.rover.api.rest.v1.response.SitterDetailResponse;
import com.rover.assertion.SitterDetailResponseAsserter;
import com.rover.domain.User;
import com.rover.domain.builder.SitterReviewBuilder;
import com.rover.domain.builder.UserBuilder;
import com.rover.domain.repository.SitterReviewRepository;
import com.rover.domain.repository.UserRepository;

import org.junit.Assert;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/test.properties")
public class SitterDetailTest {

	@Autowired
	private SitterEndpoints sitterEndpoints;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SitterReviewRepository sitterReviewRepository;

	@Test
	public void happyPathWithNoReviews() {
		User user = UserBuilder.build(userRepository).withName("Test user").withPhoneNumber("9999999999")
				.withImageUrl("http://dogs.com").withEmail("dog@internet.com").persist().get();

		SitterDetailResponse response = sitterEndpoints.getById(user.getId());

		SitterDetailResponseAsserter.assertEquals(response, user);
	}

	@Test
	public void happyPathWithMultipleReviews() {
		User owner = UserBuilder.build(userRepository).withName("Test owner").withPhoneNumber("9999999999")
				.withImageUrl("http://dogs.com").withEmail("dog@internet.com").persist().get();
		User sitter = UserBuilder.build(userRepository).withName("Test sitter").withPhoneNumber("8888888888")
				.withImageUrl("http://cats.com").withEmail("cats@internet.com").persist().get();

		SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog1,dog2").withText("was good").withRating(5)
				.withStartDate(new Date()).withEndDate(new Date()).withOwnerId(owner.getId())
				.withSitterId(sitter.getId()).persist().get();

		SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog2").withText("was bad").withRating(2)
				.withStartDate(new Date()).withEndDate(new Date()).withOwnerId(owner.getId())
				.withSitterId(sitter.getId()).persist().get();

		SitterDetailResponse response = sitterEndpoints.getById(sitter.getId());

		SitterDetailResponseAsserter.assertEquals(response, sitter);
	}
	
	@Test
	public void noUserExists(){
		try{
			sitterEndpoints.getById(1);
			throw new RuntimeException("test should have failed.");
		}
		catch(RuntimeException e){
			Assert.assertEquals("Could not find user with userId: 1", e.getMessage());
		}
	}
}
