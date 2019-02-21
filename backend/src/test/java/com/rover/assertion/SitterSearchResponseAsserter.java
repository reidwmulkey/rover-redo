package com.rover.assertion;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;

import com.rover.api.rest.v1.response.SitterSearchResponse;
import com.rover.domain.User;

public class SitterSearchResponseAsserter {

	public static void assertEquals(List<SitterSearchResponse> responses, List<User> users) {

		Assert.assertEquals(users.size(), responses.size());

		List<User> sortedUsers = users.stream()
				.sorted((u1, u2) -> u1.getSitterRank().compareTo(u2.getSitterRank()))
				.collect(Collectors.toList());

		for (int i = 0; i < sortedUsers.size(); i++) {
			assertEquals(users.get(i), responses.get(i));
		}
	}

	public static void assertEquals(User user, SitterSearchResponse response) {
		Assert.assertEquals(user.getId(), response.getId());
		Assert.assertEquals(user.getName(), response.getName());
		Assert.assertEquals(user.getEmail(), response.getEmail());
		Assert.assertEquals(user.getPhoneNumber(), response.getPhoneNumber());
		Assert.assertEquals(user.getImageUrl(), response.getImageUrl());
		Assert.assertEquals(user.getAverageStayRating(), response.getAverageStayRating());
	}
}
