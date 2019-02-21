package com.rover.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.rover.domain.event.SitterReviewCreatedEvent;
import com.rover.domain.event.SitterReviewEvent;

import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class SitterReview {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer ownerId;
	private Integer sitterId;

	private Integer rating;
	private String text;
	private String dogs;

	private Date startDate;
	private Date endDate;

	// business methods

	public SitterReviewEvent getSitterReviewCreatedEvent() {
		SitterReviewEvent event = new SitterReviewCreatedEvent();
		
		event.setOwnerId(getOwnerId());
		event.setSitterId(getSitterId());
		event.setSitterReviewId(getId());
		event.setHandled(false);
		event.setTime(new Date());
		
		return event;
	}

}
