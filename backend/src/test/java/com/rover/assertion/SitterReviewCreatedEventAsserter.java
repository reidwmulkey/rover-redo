package com.rover.assertion;

import org.junit.Assert;

import com.rover.domain.SitterReview;
import com.rover.domain.User;
import com.rover.domain.event.SitterReviewCreatedEvent;
import com.rover.domain.event.SitterReviewEvent;

public class SitterReviewCreatedEventAsserter {

	public static void assertEventWithReviewOwnerAndSitter(SitterReviewEvent event, SitterReview sitterReview,
			User owner, User sitter) {

		Assert.assertTrue(event instanceof SitterReviewCreatedEvent);
		Assert.assertEquals(event.getOwnerId(), owner.getId());
		Assert.assertEquals(event.getSitterId(), sitter.getId());
		Assert.assertEquals(event.getSitterReviewId(), sitterReview.getId());
	}
}
