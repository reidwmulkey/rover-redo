package com.rover;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rover.api.rest.v1.endpoint.SitterEndpoints;
import com.rover.api.rest.v1.response.SitterSearchResponse;
import com.rover.assertion.SitterSearchResponseAsserter;
import com.rover.domain.SitterReview;
import com.rover.domain.User;
import com.rover.domain.builder.SitterReviewBuilder;
import com.rover.domain.builder.UserBuilder;
import com.rover.domain.repository.SitterReviewRepository;
import com.rover.domain.repository.UserRepository;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/test.properties")
public class SitterSearchTest {

	@Autowired
	private SitterEndpoints sitterEndpoints;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SitterReviewRepository sitterReviewRepository;

	@Test
	public void happyPathRankAgainstEachOther() {
		User owner = UserBuilder.build(userRepository).withName("Test owner").withPhoneNumber("9999999999")
				.withImageUrl("http://dogs.com").withEmail("dog@internet.com").persist().get();
		User sitter1 = UserBuilder.build(userRepository).withName("Test sitter").withPhoneNumber("8888888888")
				.withImageUrl("http://cats.com").withEmail("cats@internet.com").persist().get();
		User sitter2 = UserBuilder.build(userRepository).withName("Test sitter").withPhoneNumber("8888888888")
				.withImageUrl("http://cats.com").withEmail("cats@internet.com").persist().get();

		SitterReview sitterReview1 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog1,dog2")
				.withText("was good").withRating(5).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter1.getId()).persist().get();

		SitterReview sitterReview2 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog2")
				.withText("was okay").withRating(3).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter1.getId()).persist().get();

		SitterReview sitterReview3 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog1,dog2")
				.withText("was good").withRating(5).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter2.getId()).persist().get();

		SitterReview sitterReview4 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog2")
				.withText("was bad").withRating(2).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter2.getId()).persist().get();

		sitter1.getSitterReviews().add(sitterReview1);
		sitter1.getSitterReviews().add(sitterReview2);
		sitter2.getSitterReviews().add(sitterReview3);
		sitter2.getSitterReviews().add(sitterReview4);

		sitter1.updateAverageStayRating();
		sitter1.updateSitterRank();
		sitter2.updateAverageStayRating();
		sitter2.updateSitterRank();


		List<SitterSearchResponse> responses = sitterEndpoints.search(null);

		SitterSearchResponseAsserter.assertEquals(responses, Arrays.asList(sitter1, sitter2));
	}

	@Test
	public void happyPathFilterSittersBelowMinimumAverageStayRating() {
		User owner = UserBuilder.build(userRepository).withName("Test owner").withPhoneNumber("9999999999")
				.withImageUrl("http://dogs.com").withEmail("dog@internet.com").persist().get();
		User sitter1 = UserBuilder.build(userRepository).withName("Test sitter").withPhoneNumber("8888888888")
				.withImageUrl("http://cats.com").withEmail("cats@internet.com").persist().get();
		User sitter2 = UserBuilder.build(userRepository).withName("Test sitter").withPhoneNumber("8888888888")
				.withImageUrl("http://cats.com").withEmail("cats@internet.com").persist().get();

		SitterReview sitterReview1 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog1,dog2")
				.withText("was terrible").withRating(1).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter1.getId()).persist().get();

		SitterReview sitterReview2 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog2")
				.withText("was terrible").withRating(1).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter1.getId()).persist().get();

		SitterReview sitterReview3 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog1,dog2")
				.withText("was good").withRating(5).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter2.getId()).persist().get();

		SitterReview sitterReview4 = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog2")
				.withText("was bad").withRating(2).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter2.getId()).persist().get();

		sitter1.getSitterReviews().add(sitterReview1);
		sitter1.getSitterReviews().add(sitterReview2);
		sitter2.getSitterReviews().add(sitterReview3);
		sitter2.getSitterReviews().add(sitterReview4);
		
		sitter1.updateAverageStayRating();
		sitter1.updateSitterRank();
		sitter2.updateAverageStayRating();
		sitter2.updateSitterRank();

		List<SitterSearchResponse> responses = sitterEndpoints.search(2.0);

		SitterSearchResponseAsserter.assertEquals(responses, Arrays.asList(sitter2));
	}

	@Test
	public void happyPathDontGetJustOwners() {
		User owner = UserBuilder.build(userRepository).withName("Test owner").withPhoneNumber("9999999999")
				.withImageUrl("http://dogs.com").withEmail("dog@internet.com").persist().get();
		User sitter = UserBuilder.build(userRepository).withName("Test sitter").withPhoneNumber("8888888888")
				.withImageUrl("http://cats.com").withEmail("cats@internet.com").persist().get();

		SitterReview sitterReview = SitterReviewBuilder.build(sitterReviewRepository).withDogs("dog1,dog2")
				.withText("was good").withRating(5).withStartDate(new Date()).withEndDate(new Date())
				.withOwnerId(owner.getId()).withSitterId(sitter.getId()).persist().get();

		sitter.getSitterReviews().add(sitterReview);
		sitter.updateAverageStayRating();
		sitter.updateSitterRank();

		List<SitterSearchResponse> responses = sitterEndpoints.search(null);

		SitterSearchResponseAsserter.assertEquals(responses, Arrays.asList(sitter));
	}

	@Test
	public void nullPathDontGetAnyUsersThatDontHaveReviews() {
		UserBuilder.build(userRepository).withName("Test user").withPhoneNumber("9999999999")
				.withImageUrl("http://dogs.com").withEmail("dog@internet.com").persist();
		UserBuilder.build(userRepository).withName("Test user has more alphabet in my name hah")
				.withPhoneNumber("8888888888").withImageUrl("http://cats.com").withEmail("cats@internet.com").persist();

		List<SitterSearchResponse> responses = sitterEndpoints.search(null);

		Assert.assertEquals(0, responses.size());
	}
}
