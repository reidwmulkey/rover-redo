package com.rover.domain.builder;

import java.util.Date;

import com.rover.domain.SitterReview;
import com.rover.domain.repository.SitterReviewRepository;

public class SitterReviewBuilder {

	private SitterReview sitterReview;
	private SitterReviewRepository sitterReviewRepository;

	public static SitterReviewBuilder build(SitterReviewRepository sitterReviewRepository) {
		return new SitterReviewBuilder(sitterReviewRepository);
	}

	public SitterReviewBuilder(SitterReviewRepository sitterReviewRepository) {
		this.sitterReviewRepository = sitterReviewRepository;
		sitterReview = new SitterReview();
	}

	// with methods

	public SitterReviewBuilder withRating(Integer rating) {
		sitterReview.setRating(rating);
		return this;
	}

	public SitterReviewBuilder withDogs(String dogs) {
		sitterReview.setDogs(dogs);
		return this;
	}

	public SitterReviewBuilder withText(String text) {
		sitterReview.setText(text);
		return this;
	}

	public SitterReviewBuilder withStartDate(Date startDate) {
		sitterReview.setStartDate(startDate);
		return this;
	}

	public SitterReviewBuilder withEndDate(Date endDate) {
		sitterReview.setEndDate(endDate);
		return this;
	}

	public SitterReviewBuilder withOwnerId(Integer ownerId) {
		sitterReview.setOwnerId(ownerId);
		return this;
	}

	public SitterReviewBuilder withSitterId(Integer sitterId) {
		sitterReview.setSitterId(sitterId);
		return this;
	}

	// access methods

	public SitterReviewBuilder persist() {
		sitterReviewRepository.save(sitterReview);
		return this;
	}

	public SitterReview get() {
		return sitterReview;
	}
}
