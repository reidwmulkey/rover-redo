package com.rover.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rover.domain.SitterReview;

public interface SitterReviewRepository extends JpaRepository<SitterReview, Integer> {

	public List<SitterReview> findAllBySitterIdEquals(Integer id);

}
