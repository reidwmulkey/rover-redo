package com.rover.api.rest.v1.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rover.api.rest.v1.response.SitterDetailResponse;
import com.rover.api.rest.v1.response.SitterSearchResponse;
import com.rover.api.rest.v1.service.SitterService;

@CrossOrigin
@RestController
public class SitterEndpoints {

	@Autowired
	private SitterService sitterService;

	@GetMapping(value = "/V1/Sitters")
	public List<SitterSearchResponse> search(@RequestParam(required = false) Double minimumAverageStayRating) {
		return sitterService.search(minimumAverageStayRating);
	}

	@GetMapping(value = "/V1/Sitters/{userId}")
	public SitterDetailResponse getById(@PathVariable Integer userId) {
		return sitterService.getDetails(userId);
	}
}
