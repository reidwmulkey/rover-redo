package com.rover.domain.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepository {
	@Autowired
	private EntityManager entityManager;

	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
}
