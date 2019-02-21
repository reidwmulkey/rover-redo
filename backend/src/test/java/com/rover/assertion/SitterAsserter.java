package com.rover.assertion;

import org.junit.Assert;

import com.rover.domain.User;
import com.rover.domain.dto.SitterReviewDto;

public class SitterAsserter {

	public static void assertDtoEqualsOwnerAndSitter(SitterReviewDto sitterReviewDto, User owner, User sitter) {
		
		Assert.assertEquals(sitterReviewDto.getOwnerName(), owner.getName());
		Assert.assertEquals(sitterReviewDto.getOwnerImageUrl(), owner.getImageUrl());
		Assert.assertEquals(sitterReviewDto.getOwnerPhoneNumber(), owner.getPhoneNumber());
		Assert.assertEquals(sitterReviewDto.getOwnerEmail(), owner.getEmail());

		Assert.assertEquals(sitterReviewDto.getSitterName(), sitter.getName());
		Assert.assertEquals(sitterReviewDto.getSitterImageUrl(), sitter.getImageUrl());
		Assert.assertEquals(sitterReviewDto.getSitterPhoneNumber(), sitter.getPhoneNumber());
		Assert.assertEquals(sitterReviewDto.getSitterEmail(), sitter.getEmail());
		
	}
}
