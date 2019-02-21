package com.rover.domain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Stopwatch;
import com.rover.domain.SitterReview;
import com.rover.domain.User;
import com.rover.domain.dto.SitterReviewDto;
import com.rover.domain.event.repository.SitterReviewCreatedEventRepository;
import com.rover.domain.repository.SitterReviewRepository;
import com.rover.domain.repository.UserRepository;
import com.rover.util.DateUtil;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ImportService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SitterReviewRepository sitterReviewRepository;
	@Autowired
	private SitterReviewCreatedEventRepository sitterReviewCreatedEventRepository;

	@SneakyThrows
	@Transactional
	public void importFromSitterReviewsCSV(String csv) {

		String[] reviewStrings = csv.split("\n");
		Map<String, User> emailToUserMap = new HashMap<>();

		log.info("Importing data from a SitterReview CSV feed.");
		Stopwatch sw = Stopwatch.createStarted();

		// using old school int i = x... so we can skip the headers
		// 1 flyby to create users, which will make the review pass easier
		for (int i = 1; i < reviewStrings.length; i++) {
			SitterReviewDto sitterReviewDto = new SitterReviewDto(reviewStrings[i]);

			if (!emailToUserMap.containsKey(sitterReviewDto.getSitterEmail())) {
				emailToUserMap.put(sitterReviewDto.getSitterEmail(),
						new User(sitterReviewDto.getSitterName(), sitterReviewDto.getSitterEmail(),
								sitterReviewDto.getSitterImageUrl(), sitterReviewDto.getSitterPhoneNumber()));
			}

			if (!emailToUserMap.containsKey(sitterReviewDto.getOwnerEmail())) {
				emailToUserMap.put(sitterReviewDto.getOwnerEmail(),
						new User(sitterReviewDto.getOwnerName(), sitterReviewDto.getOwnerEmail(),
								sitterReviewDto.getOwnerImageUrl(), sitterReviewDto.getOwnerPhoneNumber()));
			}
		}

		for (User user : emailToUserMap.values()) {
			userRepository.save(user);
		}

		log.info("Created " + emailToUserMap.values().size() + " users from the SiterReview CSV feed in "
				+ sw.elapsed(TimeUnit.MILLISECONDS) + "ms");
		sw = Stopwatch.createStarted();

		for (int i = 1; i < reviewStrings.length; i++) {
			SitterReviewDto sitterReviewDto = new SitterReviewDto(reviewStrings[i]);

			SitterReview sitterReview = new SitterReview();
			sitterReview.setText(sitterReviewDto.getText());
			sitterReview.setRating(Integer.parseInt(sitterReviewDto.getRating()));
			sitterReview.setDogs(sitterReviewDto.getDogs().replaceAll("\\|", ","));

			sitterReview.setStartDate(DateUtil.DATE_FORMATTER.parse(sitterReviewDto.getStartDate()));
			sitterReview.setEndDate(DateUtil.DATE_FORMATTER.parse(sitterReviewDto.getEndDate()));

			sitterReview.setOwnerId(emailToUserMap.get(sitterReviewDto.getOwnerEmail()).getId());
			sitterReview.setSitterId(emailToUserMap.get(sitterReviewDto.getSitterEmail()).getId());

			sitterReviewRepository.save(sitterReview);
			sitterReviewCreatedEventRepository.save(sitterReview.getSitterReviewCreatedEvent());
		}

		log.info("Created " + (reviewStrings.length - 1) + " SitterReviews from the SiterReview CSV feed in "
				+ sw.elapsed(TimeUnit.MILLISECONDS) + "ms");
	}
}
