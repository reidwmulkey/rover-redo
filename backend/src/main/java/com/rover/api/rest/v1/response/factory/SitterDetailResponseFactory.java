package com.rover.api.rest.v1.response.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rover.api.rest.v1.response.SitterDetailResponse;
import com.rover.domain.User;

@Component
public class SitterDetailResponseFactory {

	@Autowired
	private SitterReviewResponseFactory sitterReviewResponseFactory;

	public SitterDetailResponse build(User user) {

		SitterDetailResponse response = new SitterDetailResponse();

		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());
		response.setImageUrl(user.getImageUrl());
		response.setPhoneNumber(user.getPhoneNumber());
		response.setAverageStayRating(user.getAverageStayRating());

		response.setSitterReviews(sitterReviewResponseFactory.build(user.getSitterReviews()));

		return response;
	}
}
