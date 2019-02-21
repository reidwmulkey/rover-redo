package com.rover.api.rest.v1.response;

import lombok.Data;

@Data
public class SitterSearchResponse {

	private Integer id;

	private String name;
	private String email;
	private String imageUrl;
	private String phoneNumber;

	private Double averageStayRating;

}
