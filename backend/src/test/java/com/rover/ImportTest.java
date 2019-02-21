package com.rover;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rover.api.rest.v1.endpoint.ImportEndpoints;
import com.rover.assertion.SitterAsserter;
import com.rover.assertion.SitterReviewAsserter;
import com.rover.assertion.SitterReviewCreatedEventAsserter;
import com.rover.domain.SitterReview;
import com.rover.domain.User;
import com.rover.domain.dto.SitterReviewDto;
import com.rover.domain.event.SitterReviewEvent;
import com.rover.domain.event.repository.SitterReviewCreatedEventRepository;
import com.rover.domain.repository.SitterReviewRepository;
import com.rover.domain.repository.UserRepository;
import com.rover.util.DateUtil;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("/test.properties")
public class ImportTest {

	private String headers = "rating,sitter_image,end_date,text,owner_image,dogs,sitter,owner,start_date,sitter_phone_number,sitter_email,owner_phone_number,owner_email\n";
	private String import1Review = "5,https://images.dog.ceo/breeds/dalmatian/cooper2.jpg,2013-04-08,Donec lacus justo luctus tellus nisl penatibus mus massa ipsum odio. Lorem dolor. Fames lorem ligula fusce condimentum dis mauris. Metus nulla quam mus duis congue volutpat et ipsum ad. Purus netus a viverra et sapien et pharetra quis nullam posuere amet sem convallis etiam sagittis vel. Nulla donec suspendisse sagittis hymenaeos mi. Metus risus enim egestas. Fames vitae mus vivamus eu ad donec cum elit consectetuer. Purus magna per rutrum fusce condimentum habitant quis pretium ac egestas diam sapien leo tortor rutrum. Felis vitae fames velit. Ipsum morbi interdum. Neque justo gravida cras at.,https://images.dog.ceo/breeds/hound-ibizan/n02091244_327.jpg,Pinot Grigio,Lauren B.,Shelli K.,2013-02-26,+12546478758,user4739@gmail.com,+15817557107,user2555@verizon.net";
	private String importWithSameUsers = "5,https://images.dog.ceo/breeds/hound-ibizan/n02091244_327.jpg,2013-04-08,Donec lacus justo luctus tellus nisl penatibus mus massa ipsum odio. Lorem dolor. Fames lorem ligula fusce condimentum dis mauris. Metus nulla quam mus duis congue volutpat et ipsum ad. Purus netus a viverra et sapien et pharetra quis nullam posuere amet sem convallis etiam sagittis vel. Nulla donec suspendisse sagittis hymenaeos mi. Metus risus enim egestas. Fames vitae mus vivamus eu ad donec cum elit consectetuer. Purus magna per rutrum fusce condimentum habitant quis pretium ac egestas diam sapien leo tortor rutrum. Felis vitae fames velit. Ipsum morbi interdum. Neque justo gravida cras at.,https://images.dog.ceo/breeds/dalmatian/cooper2.jpg,Pinot Grigio,Shelli K.,Lauren B.,2013-02-26,+15817557107,user2555@verizon.net,+12546478758,user4739@gmail.com";
	private String importNonNumericRating = "5a,https://images.dog.ceo/breeds/dalmatian/cooper2.jpg,2013-04-08,Donec lacus justo luctus tellus nisl penatibus mus massa ipsum odio. Lorem dolor. Fames lorem ligula fusce condimentum dis mauris. Metus nulla quam mus duis congue volutpat et ipsum ad. Purus netus a viverra et sapien et pharetra quis nullam posuere amet sem convallis etiam sagittis vel. Nulla donec suspendisse sagittis hymenaeos mi. Metus risus enim egestas. Fames vitae mus vivamus eu ad donec cum elit consectetuer. Purus magna per rutrum fusce condimentum habitant quis pretium ac egestas diam sapien leo tortor rutrum. Felis vitae fames velit. Ipsum morbi interdum. Neque justo gravida cras at.,https://images.dog.ceo/breeds/hound-ibizan/n02091244_327.jpg,Pinot Grigio,Lauren B.,Shelli K.,2013-02-26,+12546478758,user2555@verizon.net,+15817557107,user4739@gmail.com";
	private String importRatingOutOfRange = "-5,https://images.dog.ceo/breeds/dalmatian/cooper2.jpg,2013-04-08,Donec lacus justo luctus tellus nisl penatibus mus massa ipsum odio. Lorem dolor. Fames lorem ligula fusce condimentum dis mauris. Metus nulla quam mus duis congue volutpat et ipsum ad. Purus netus a viverra et sapien et pharetra quis nullam posuere amet sem convallis etiam sagittis vel. Nulla donec suspendisse sagittis hymenaeos mi. Metus risus enim egestas. Fames vitae mus vivamus eu ad donec cum elit consectetuer. Purus magna per rutrum fusce condimentum habitant quis pretium ac egestas diam sapien leo tortor rutrum. Felis vitae fames velit. Ipsum morbi interdum. Neque justo gravida cras at.,https://images.dog.ceo/breeds/hound-ibizan/n02091244_327.jpg,Pinot Grigio,Lauren B.,Shelli K.,2013-02-26,+12546478758,user2555@verizon.net,+15817557107,user4739@gmail.com";
	private String importBadStartDate = "5,https://images.dog.ceo/breeds/dalmatian/cooper2.jpg,2013-04-08,Donec lacus justo luctus tellus nisl penatibus mus massa ipsum odio. Lorem dolor. Fames lorem ligula fusce condimentum dis mauris. Metus nulla quam mus duis congue volutpat et ipsum ad. Purus netus a viverra et sapien et pharetra quis nullam posuere amet sem convallis etiam sagittis vel. Nulla donec suspendisse sagittis hymenaeos mi. Metus risus enim egestas. Fames vitae mus vivamus eu ad donec cum elit consectetuer. Purus magna per rutrum fusce condimentum habitant quis pretium ac egestas diam sapien leo tortor rutrum. Felis vitae fames velit. Ipsum morbi interdum. Neque justo gravida cras at.,https://images.dog.ceo/breeds/hound-ibizan/n02091244_327.jpg,Pinot Grigio,Lauren B.,Shelli K.,2013-012-26,+12546478758,user2555@verizon.net,+15817557107,user4739@gmail.com";
	private String importBadEndDate = "5,https://images.dog.ceo/breeds/dalmatian/cooper2.jpg,2013-04-080,Donec lacus justo luctus tellus nisl penatibus mus massa ipsum odio. Lorem dolor. Fames lorem ligula fusce condimentum dis mauris. Metus nulla quam mus duis congue volutpat et ipsum ad. Purus netus a viverra et sapien et pharetra quis nullam posuere amet sem convallis etiam sagittis vel. Nulla donec suspendisse sagittis hymenaeos mi. Metus risus enim egestas. Fames vitae mus vivamus eu ad donec cum elit consectetuer. Purus magna per rutrum fusce condimentum habitant quis pretium ac egestas diam sapien leo tortor rutrum. Felis vitae fames velit. Ipsum morbi interdum. Neque justo gravida cras at.,https://images.dog.ceo/breeds/hound-ibizan/n02091244_327.jpg,Pinot Grigio,Lauren B.,Shelli K.,2013-02-26,+12546478758,user2555@verizon.net,+15817557107,user4739@gmail.com";
	private String importMissingColumns = "https://images.dog.ceo/breeds/dalmatian/cooper2.jpg,2013-04-08,Donec lacus justo luctus tellus nisl penatibus mus massa ipsum odio. Lorem dolor. Fames lorem ligula fusce condimentum dis mauris. Metus nulla quam mus duis congue volutpat et ipsum ad. Purus netus a viverra et sapien et pharetra quis nullam posuere amet sem convallis etiam sagittis vel. Nulla donec suspendisse sagittis hymenaeos mi. Metus risus enim egestas. Fames vitae mus vivamus eu ad donec cum elit consectetuer. Purus magna per rutrum fusce condimentum habitant quis pretium ac egestas diam sapien leo tortor rutrum. Felis vitae fames velit. Ipsum morbi interdum. Neque justo gravida cras at.,https://images.dog.ceo/breeds/hound-ibizan/n02091244_327.jpg,Pinot Grigio,Lauren B.,Shelli K.,2013-02-26,+12546478758,user2555@verizon.net,+15817557107,user4739@gmail.com";

	@Autowired
	private ImportEndpoints importEndpoints;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SitterReviewRepository sitterReviewRepository;
	@Autowired
	private SitterReviewCreatedEventRepository sitterReviewCreatedEventRepository;

	private void assertNoValuesCreated(){
		List<User> users = userRepository.findAll();
		Assert.assertEquals(users.size(), 0);

		List<SitterReview> sitterReviews = sitterReviewRepository.findAll();
		Assert.assertEquals(sitterReviews.size(), 0);

		List<SitterReviewEvent> sitterReviewCreatedEvents = sitterReviewCreatedEventRepository.findAll();
		Assert.assertEquals(sitterReviewCreatedEvents.size(), 0);
	}
	
	@Test
	public void happyPathImport1review() {
		importEndpoints.importFromSitterReviewsCSV(headers + import1Review);

		List<User> users = userRepository.findAll();

		Assert.assertEquals(users.size(), 2);
		User owner = users.get(0);
		User sitter = users.get(1);

		SitterReviewDto sitterReviewDto = new SitterReviewDto(import1Review);
		SitterAsserter.assertDtoEqualsOwnerAndSitter(sitterReviewDto, owner, sitter);

		List<SitterReview> sitterReviews = sitterReviewRepository.findAll();
		Assert.assertEquals(sitterReviews.size(), 1);

		SitterReviewAsserter.assertDtoEqualsReviewWithOwnerAndSitter(sitterReviewDto, sitterReviews.get(0), owner,
				sitter);

		List<SitterReviewEvent> sitterReviewCreatedEvents = sitterReviewCreatedEventRepository.findAll();
		Assert.assertEquals(sitterReviewCreatedEvents.size(), 1);

		SitterReviewCreatedEventAsserter.assertEventWithReviewOwnerAndSitter(sitterReviewCreatedEvents.get(0),
				sitterReviews.get(0), owner, sitter);
	}

	@Test
	public void happyPathImportMultiReviewSameUsers() {

		importEndpoints.importFromSitterReviewsCSV(headers + import1Review + "\n" + importWithSameUsers);

		List<User> users = userRepository.findAll();

		Assert.assertEquals(users.size(), 2);
		User ownerFromFirstLine = users.get(0);
		User sitterFromFirstLine = users.get(1);

		SitterReviewDto sitterReviewDto1 = new SitterReviewDto(import1Review);
		SitterAsserter.assertDtoEqualsOwnerAndSitter(sitterReviewDto1, ownerFromFirstLine, sitterFromFirstLine);
		SitterReviewDto sitterReviewDto2 = new SitterReviewDto(importWithSameUsers);
		SitterAsserter.assertDtoEqualsOwnerAndSitter(sitterReviewDto2, sitterFromFirstLine, ownerFromFirstLine);

		List<SitterReview> sitterReviews = sitterReviewRepository.findAll();
		Assert.assertEquals(sitterReviews.size(), 2);

		SitterReviewAsserter.assertDtoEqualsReviewWithOwnerAndSitter(sitterReviewDto1, sitterReviews.get(0),
				ownerFromFirstLine, sitterFromFirstLine);
		SitterReviewAsserter.assertDtoEqualsReviewWithOwnerAndSitter(sitterReviewDto2, sitterReviews.get(1),
				sitterFromFirstLine, ownerFromFirstLine);

		List<SitterReviewEvent> sitterReviewCreatedEvents = sitterReviewCreatedEventRepository.findAll();
		Assert.assertEquals(sitterReviewCreatedEvents.size(), 2);

		SitterReviewCreatedEventAsserter.assertEventWithReviewOwnerAndSitter(sitterReviewCreatedEvents.get(0),
				sitterReviews.get(0), ownerFromFirstLine, sitterFromFirstLine);
		SitterReviewCreatedEventAsserter.assertEventWithReviewOwnerAndSitter(sitterReviewCreatedEvents.get(1),
				sitterReviews.get(1), sitterFromFirstLine, ownerFromFirstLine);
	}

	@Test
	public void grumpyPathImportNonNumericRating() {
		try {
			importEndpoints.importFromSitterReviewsCSV(headers + import1Review + "\n" + importNonNumericRating);
		} catch (Exception e) {
			Assert.assertEquals("Found non-numeric rating: 5a for input: " + importNonNumericRating, e.getMessage());
		}

		assertNoValuesCreated();
	}
	
	@Test
	public void grumpyPathImportRatingOutOfRange() {
		try {
			importEndpoints.importFromSitterReviewsCSV(headers + import1Review + "\n" + importRatingOutOfRange);
		} catch (Exception e) {
			Assert.assertEquals("Found rating not between 0 and 5: -5 for input: " + importRatingOutOfRange, e.getMessage());
		}

		assertNoValuesCreated();
	}

	@Test
	public void grumpyPathImportBadStartDate() {
		try {
			importEndpoints.importFromSitterReviewsCSV(headers + import1Review + "\n" + importBadStartDate);
		} catch (Exception e) {
			Assert.assertEquals("Found startDate: 2013-012-26 that did not match pattern: "
					+ DateUtil.DATE_PATTERN + " for input: " + importBadStartDate, e.getMessage());
		}

		assertNoValuesCreated();
	}
	
	@Test
	public void grumpyPathImportBadEndDate() {
		try {
			importEndpoints.importFromSitterReviewsCSV(headers + import1Review + "\n" + importBadEndDate);
		} catch (Exception e) {
			Assert.assertEquals("Found endDate: 2013-04-080 that did not match pattern: "
					+ DateUtil.DATE_PATTERN + " for input: " + importBadEndDate, e.getMessage());
		}

		assertNoValuesCreated();
	}

	@Test
	public void grumpyPathImportWithBadColumns() {
		try {
			importEndpoints.importFromSitterReviewsCSV(headers + import1Review + "\n" + importMissingColumns);
		} catch (Exception e) {
			Assert.assertEquals("Expected 13 columns but instead found 12 for input: " + importMissingColumns,
					e.getMessage());
		}

		assertNoValuesCreated();
	}

}
