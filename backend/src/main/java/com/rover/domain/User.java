package com.rover.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rover.util.AlphabetUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@NoArgsConstructor
public class User {

	@Id
	@Getter
	@Setter
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String email;
	@Getter
	@Setter
	private String imageUrl;
	@Getter
	@Setter
	private String phoneNumber;

	@Getter
	@Setter
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "sitterId")
	private List<SitterReview> sitterReviews = new ArrayList<>();

	@Getter
	private Double sitterRank;
	@Getter
	private Double averageStayRating;

	// constructors

	public User(String name, String email, String imageUrl, String phoneNumber) {
		setName(name);
		setEmail(email);
		setImageUrl(imageUrl);
		setPhoneNumber(phoneNumber);
	}

	// business methods

	public void updateAverageStayRating() {
		setAverageStayRating(getCalculatedAverageStayRating());
	}

	public void updateSitterRank() {
		setSitterRank(getCalculatedSitterRank());
	}

	public Double getCalculatedAverageStayRating() {
		if (sitterReviews.isEmpty()) {
			return null;
		}

		return ((double) sitterReviews.stream().map(r -> r.getRating()).reduce(0, Integer::sum).intValue())
				/ sitterReviews.size();
	}

	public Double getCalculatedSitterRank() {
		Double weightedSitterScore = getSitterScore() * getSitterScoreWeight();
		Double weightedRatingScore = getRatingScore() * getRatingScoreWeight();
		return weightedRatingScore + weightedSitterScore;
	}

	// private methods

	private Double getSitterScoreWeight() {
		return 1 - getRatingScoreWeight();
	}

	private Double getRatingScoreWeight() {
		return Math.min((double) sitterReviews.size() / 10, 1.0);
	}

	private Double getRatingScore() {
		return getCalculatedAverageStayRating() != null ? getCalculatedAverageStayRating() : 0.0;
	}

	private Double getSitterScore() {
		Integer numberOfCharactersRepresented = AlphabetUtil
				.getDistinctLowerCaseCharactersInString(getName().toLowerCase()).size();
		Double fractionOfUSAlphabetRepresented = ((double) numberOfCharactersRepresented)
				/ ((double) AlphabetUtil.lowerCaseAlphabet.size());
		return 5 * fractionOfUSAlphabetRepresented;
	}
	
	private void setSitterRank(Double sitterRank){
		this.sitterRank = sitterRank;
	}

	private void setAverageStayRating(Double averageStayRating){
		this.averageStayRating = averageStayRating;
	}

}
