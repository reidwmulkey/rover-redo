package com.rover.api.rest.v1.response;

import java.util.Date;

import lombok.Data;

@Data
public class SitterReviewResponse {

	private Integer id;
	private Integer ownerId;
	private Integer sitterId;

	private Integer rating;
	private String text;
	private String dogs;

	private Date startDate;
	private Date endDate;

}
