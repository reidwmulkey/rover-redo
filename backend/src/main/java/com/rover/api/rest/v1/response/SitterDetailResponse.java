package com.rover.api.rest.v1.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SitterDetailResponse {

	private Integer id;

	private String name;
	private String email;
	private String imageUrl;
	private String phoneNumber;

	private Double averageStayRating;

	private List<SitterReviewResponse> sitterReviews = new ArrayList<>();
}
