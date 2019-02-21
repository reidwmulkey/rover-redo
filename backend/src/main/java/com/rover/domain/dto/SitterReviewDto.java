package com.rover.domain.dto;

import java.util.Arrays;
import java.util.List;

import com.rover.util.DateUtil;
import com.rover.util.StringUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SitterReviewDto {

	private String rawInput;

	private String rating;
	private String text;
	private String startDate;
	private String endDate;

	private String dogs;

	private String sitterName;
	private String sitterImageUrl;
	private String sitterEmail;
	private String sitterPhoneNumber;

	private String ownerName;
	private String ownerImageUrl;
	private String ownerEmail;
	private String ownerPhoneNumber;

	// constructors

	public SitterReviewDto(String reviewString) {

		setRawInput(reviewString);

		List<String> columns = Arrays.asList(reviewString.split(","));

		if (columns.size() != 13) {
			throw new RuntimeException(
					"Expected 13 columns but instead found " + columns.size() + " for input: " + getRawInput());
		}

		setRating(columns.get(0));
		setSitterImageUrl(columns.get(1));
		setEndDate(columns.get(2));
		setText(columns.get(3));
		setOwnerImageUrl(columns.get(4));
		setDogs(columns.get(5));
		setSitterName(columns.get(6));
		setOwnerName(columns.get(7));
		setStartDate(columns.get(8));
		setSitterPhoneNumber(columns.get(9));
		setSitterEmail(columns.get(10));
		setOwnerPhoneNumber(columns.get(11));
		setOwnerEmail(columns.get(12));

		validateData();
	}

	public void validateData() {

		validateRating();

		if (!getStartDate().matches(DateUtil.DATE_PATTERN_MATCHER)) {
			throw new RuntimeException("Found startDate: " + getStartDate() + " that did not match pattern: "
					+ DateUtil.DATE_PATTERN + " for input: " + getRawInput());
		}

		if (!getEndDate().matches(DateUtil.DATE_PATTERN_MATCHER)) {
			throw new RuntimeException("Found endDate: " + getEndDate() + " that did not match pattern: "
					+ DateUtil.DATE_PATTERN + " for input: " + getRawInput());
		}
	}

	private void validateRating(){
		
		if (!StringUtil.isNumeric(getRating())) {
			throw new RuntimeException("Found non-numeric rating: " + getRating() + " for input: " + getRawInput());
		}
		
		Integer r = Integer.parseInt(getRating());
		if (r < 0 || r > 5) {
			throw new RuntimeException("Found rating not between 0 and 5: " + getRating() + " for input: " + getRawInput());
		}
	}
}
