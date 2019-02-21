package com.rover.assertion;

import org.junit.Assert;

import com.rover.domain.SitterReview;
import com.rover.domain.User;
import com.rover.domain.dto.SitterReviewDto;

public class SitterReviewAsserter {

	public static void assertDtoEqualsReviewWithOwnerAndSitter(SitterReviewDto sitterReviewDto,
			SitterReview sitterReview, User owner, User sitter) {

		Assert.assertEquals(Integer.parseInt(sitterReviewDto.getRating()), (int) sitterReview.getRating());
		Assert.assertEquals(sitterReviewDto.getDogs(), sitterReview.getDogs());
		Assert.assertEquals(sitterReviewDto.getText(), sitterReview.getText());
		Assert.assertEquals(owner.getId(), sitterReview.getOwnerId());
		Assert.assertEquals(sitter.getId(), sitterReview.getSitterId());
	}
}
