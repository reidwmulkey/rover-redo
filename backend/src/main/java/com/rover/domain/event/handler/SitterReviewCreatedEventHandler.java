package com.rover.domain.event.handler;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rover.domain.User;
import com.rover.domain.event.SitterReviewEvent;
import com.rover.domain.event.repository.SitterReviewCreatedEventRepository;
import com.rover.domain.repository.UserRepository;

@Component
public class SitterReviewCreatedEventHandler {

	@Autowired
	private SitterReviewCreatedEventRepository sitterReviewCreatedEventRepository;
	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Scheduled(fixedDelay = 1000, initialDelay = 2000)
	public void handleNewSitterReviewCreatedEvents() {

		List<SitterReviewEvent> events = sitterReviewCreatedEventRepository.findAllByHandledEquals(false);

		events.forEach(event -> {
			User user = userRepository.findOne(event.getSitterId());
		
			user.updateSitterRank();
			user.updateAverageStayRating();
			
			event.handle();
		});
	}
}
