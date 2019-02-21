package com.rover.domain.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.rover.domain.User;

@Component
@SuppressWarnings("unchecked")
public class SitterSearchRepository extends AbstractRepository {

	public List<User> searchAllSittersByAverageStayRatingGreaterThanOrEqualTo(Double minimumAverageStay) {

		Criteria criteria = getSession().createCriteria(User.class).add(Restrictions.isNotNull("sitterRank"))
				.addOrder(Order.desc("sitterRank"));

		if (minimumAverageStay != null) {
			criteria.add(Restrictions.ge("averageStayRating", minimumAverageStay));
		}

		return criteria.list();
	}

}
