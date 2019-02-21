package com.rover.api.rest.v1.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;
import com.rover.api.rest.v1.response.SitterDetailResponse;
import com.rover.api.rest.v1.response.SitterSearchResponse;
import com.rover.api.rest.v1.response.factory.SitterDetailResponseFactory;
import com.rover.api.rest.v1.response.factory.SitterSearchResponseFactory;
import com.rover.domain.User;
import com.rover.domain.repository.SitterSearchRepository;
import com.rover.domain.repository.UserRepository;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class SitterService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SitterSearchRepository sitterSearchRepository;
	@Autowired
	private SitterSearchResponseFactory sitterSearchResponseFactory;
	@Autowired
	private SitterDetailResponseFactory sitterDetailResponseFactory;

	public List<SitterSearchResponse> search(Double minimumAverageStayRating) {

		Stopwatch sw = Stopwatch.createStarted();
		log.info("Searching for sitters using a minimumAverageStayRating: " + minimumAverageStayRating);

		List<User> sitters = sitterSearchRepository
				.searchAllSittersByAverageStayRatingGreaterThanOrEqualTo(minimumAverageStayRating);

		List<SitterSearchResponse> responses = sitterSearchResponseFactory.build(sitters);
		log.info("Retrieved " + responses.size() + " Sitters in " + sw.elapsed(TimeUnit.MILLISECONDS) + "ms.");

		return responses;

	}

	public SitterDetailResponse getDetails(Integer userId) {

		Stopwatch sw = Stopwatch.createStarted();
		log.info("Retrieving SitterDetails for userId: " + userId);

		User user = userRepository.findOne(userId);

		if (user == null) {
			throw new RuntimeException("Could not find user with userId: " + userId);
		}

		SitterDetailResponse response = sitterDetailResponseFactory.build(user);
		log.info("Retrieved SitterDetails for userId: " + userId + " in " + sw.elapsed(TimeUnit.MILLISECONDS) + "ms.");

		return response;
	}
}
