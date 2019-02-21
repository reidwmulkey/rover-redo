package com.rover.api.rest.v1.response.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rover.api.rest.v1.response.SitterSearchResponse;
import com.rover.domain.User;

@Component
public class SitterSearchResponseFactory {

	public List<SitterSearchResponse> build(List<User> users) {
		List<SitterSearchResponse> responses = new ArrayList<>();

		users.forEach(user -> responses.add(build(user)));

		return responses;
	}

	public SitterSearchResponse build(User user) {
		SitterSearchResponse response = new SitterSearchResponse();

		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());
		response.setImageUrl(user.getImageUrl());
		response.setPhoneNumber(user.getPhoneNumber());
		response.setAverageStayRating(user.getAverageStayRating());

		return response;
	}
}
