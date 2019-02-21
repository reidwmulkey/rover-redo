package com.rover.api.rest.v1.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rover.domain.service.ImportService;

@RestController
public class ImportEndpoints {

	@Autowired
	private ImportService importService;

	@PostMapping(value = "/V1/Imports/SitterReviewsFromCSV")
	public void importFromSitterReviewsCSV(@RequestBody String csv) {
		importService.importFromSitterReviewsCSV(csv);
	}
}
