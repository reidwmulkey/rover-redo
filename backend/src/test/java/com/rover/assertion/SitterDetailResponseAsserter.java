package com.rover.assertion;

import org.junit.Assert;

import com.rover.api.rest.v1.response.SitterDetailResponse;
import com.rover.domain.User;

public class SitterDetailResponseAsserter {

	public static void assertEquals(SitterDetailResponse response, User user) {

		Assert.assertEquals(response.getName(), user.getName());
		Assert.assertEquals(response.getEmail(), user.getEmail());
		Assert.assertEquals(response.getPhoneNumber(), user.getPhoneNumber());
		Assert.assertEquals(response.getImageUrl(), user.getImageUrl());
		Assert.assertEquals(response.getAverageStayRating(), user.getAverageStayRating());

		Assert.assertEquals(response.getSitterReviews().size(), user.getSitterReviews().size());

		SitterReviewResponseAsserter.assertEquals(response.getSitterReviews(), user.getSitterReviews());
	}
}
