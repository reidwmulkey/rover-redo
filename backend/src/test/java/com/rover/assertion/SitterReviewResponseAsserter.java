package com.rover.assertion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.rover.api.rest.v1.response.SitterReviewResponse;
import com.rover.domain.SitterReview;

public class SitterReviewResponseAsserter {

	public static void assertEquals(List<SitterReviewResponse> responses, List<SitterReview> reviews) {
		Assert.assertEquals(responses.size(), reviews.size());

		Map<Integer, SitterReviewResponse> idToResponseMap = new HashMap<>();

		responses.forEach(response -> idToResponseMap.put(response.getId(), response));

		reviews.forEach(review -> {
			SitterReviewResponse response = idToResponseMap.get(review.getId());
			assertEquals(response, review);
		});
	}

	public static void assertEquals(SitterReviewResponse response, SitterReview review) {
		Assert.assertEquals(response.getId(), review.getId());
		Assert.assertEquals(response.getDogs(), review.getDogs());
		Assert.assertEquals(response.getOwnerId(), review.getOwnerId());
		Assert.assertEquals(response.getSitterId(), review.getSitterId());
		Assert.assertEquals(response.getRating(), review.getRating());
		Assert.assertEquals(response.getText(), review.getText());
		Assert.assertEquals(response.getStartDate(), review.getStartDate());
		Assert.assertEquals(response.getEndDate(), review.getEndDate());
	}
}
