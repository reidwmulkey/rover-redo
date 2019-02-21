package com.rover.domain.event;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SitterReviewCreatedEvent")
public class SitterReviewCreatedEvent extends SitterReviewEvent {

}
