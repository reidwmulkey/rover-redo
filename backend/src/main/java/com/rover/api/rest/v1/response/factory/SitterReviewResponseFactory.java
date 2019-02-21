package com.rover.api.rest.v1.response.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rover.api.rest.v1.response.SitterReviewResponse;
import com.rover.domain.SitterReview;

@Component
public class SitterReviewResponseFactory {

	public List<SitterReviewResponse> build(List<SitterReview> sitterReviews) {
		List<SitterReviewResponse> responses = new ArrayList<>();

		sitterReviews.forEach(sitterReview -> responses.add(build(sitterReview)));

		return responses;
	}

	public SitterReviewResponse build(SitterReview sitterReview) {
		SitterReviewResponse response = new SitterReviewResponse();

		response.setId(sitterReview.getId());
		response.setOwnerId(sitterReview.getOwnerId());
		response.setSitterId(sitterReview.getSitterId());
		response.setRating(sitterReview.getRating());
		response.setText(sitterReview.getText());
		response.setDogs(sitterReview.getDogs());
		response.setStartDate(sitterReview.getStartDate());
		response.setEndDate(sitterReview.getEndDate());

		return response;
	}
}
