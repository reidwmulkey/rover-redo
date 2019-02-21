package com.rover.domain.event.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rover.domain.event.SitterReviewEvent;

public interface SitterReviewCreatedEventRepository extends JpaRepository<SitterReviewEvent, Integer> {

	public List<SitterReviewEvent> findAllByHandledEquals(boolean handled);
}
