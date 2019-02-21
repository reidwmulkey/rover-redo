package com.rover;

import org.junit.Assert;
import org.junit.Test;

import com.rover.domain.SitterReview;
import com.rover.domain.User;
import com.rover.util.AlphabetUtil;

public class UserTest {

	private void add5StarReviewToUser(User user) {
		SitterReview sitterReview = new SitterReview();
		sitterReview.setRating(5);
		user.getSitterReviews().add(sitterReview);
	}

	@Test
	public void happyPathFromGithub() {
		User user = new User();
		user.setName("abcdefghijklm");

		Assert.assertEquals(13, AlphabetUtil.getDistinctLowerCaseCharactersInString(user.getName()).size());

		// sitter rank fully to sitter score side because there are 0 reviews
		Assert.assertEquals(new Double(2.5), user.getCalculatedSitterRank());

		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(2.75), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(3.0), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(3.25), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(3.5), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(3.75), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(4.0), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(4.25), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(4.5), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(4.75), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(5.0), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(5.0), user.getCalculatedSitterRank());
		add5StarReviewToUser(user);
		Assert.assertEquals(new Double(5.0), user.getCalculatedSitterRank());
	}

}
