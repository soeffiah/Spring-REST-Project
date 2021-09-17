package com.workshop.project;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MyRestClient {

	private static final Logger logger = LoggerFactory.getLogger(MyRestClient.class);
	
	private static final String NAME_PARAM = "name";
	private static final String ALIGNMENT_PARAM = "alignment";
	private static final String FULLNAME_PARAM = "full name";
	private static final String GENDER_PARAM = "gender";
	private static final String RACE_PARAM = "race";
	private static final String PUBLISHER_PARAM = "publisher";

	private static final String LIMIT_PARAM = "limit";
	private static final String PAGE_PARAM = "page";

	@Autowired
	private RestTemplate myRestTemplate;

	@Value("${myrest.url}")
	private String restUrl;

	// GET /api/supers
	public SuperDTO[] getAllSupers() {

		String finalUrl = restUrl;
		logger.info("GET to " + finalUrl);
		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}

	// GET /api/supers/{id}
	public SuperDTO getSuper(int id) {

		String finalUrl = restUrl + "/" + id;
		logger.info("GET to " + finalUrl);
		return myRestTemplate.getForObject(finalUrl, SuperDTO.class);

	}
	
	// GET /api/supers?name=XXX
	public SuperDTO[] filterName(String name) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(NAME_PARAM, name);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}


	// GET /api/supers?alignment=XXX
	public SuperDTO[] filterAlignment(String alignment) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(ALIGNMENT_PARAM, alignment);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}
	
	// GET /api/supers?full name=XXX
	public SuperDTO[] filterFullName(String fullName) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(FULLNAME_PARAM, fullName);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}
	
	// GET /api/supers?gender=XXX
	public SuperDTO[] filterGender(String gender) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(GENDER_PARAM, gender);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}
	
	// GET /api/supers?race=XXX
	public SuperDTO[] filterRace(String race) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(RACE_PARAM, race);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}
	
	// GET /api/supers?publisher=XXX
	public SuperDTO[] filterPublisher(String publisher) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(PUBLISHER_PARAM, publisher);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}
	
	// GET /api/supers?alignment=XXX&gender=XXX
	public SuperDTO[] filterAlignmentAndGender(String alignment, String gender) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(ALIGNMENT_PARAM, alignment).queryParam(GENDER_PARAM, gender);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}

	// GET /api/supers?limit=X&start=Y
	public SuperDTO[] ResultPagination(int limit, int page) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(restUrl).queryParam(LIMIT_PARAM, limit).queryParam(PAGE_PARAM, page);

		String finalUrl = uriBuilder.toUriString();
		logger.info("GET to " + finalUrl);

		return myRestTemplate.getForObject(finalUrl, SuperDTO[].class);

	}

	// POST /api/supers
	public String addSuper(SuperDTO sup) {

		String finalUrl = restUrl;
		logger.info("POST to " + finalUrl);

		URI destination = null;
		try {
			destination = new URI(finalUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		URI result = myRestTemplate.postForLocation(destination, sup);

		return result.toString();
	}

	// PUT /api/supers/{id}
	public void modifySuper(SuperDTO sup, int id) {

		String finalUrl = restUrl + "/" + id;
		logger.info("PUT to " + finalUrl);

		URI destination = null;
		try {
			destination = new URI(finalUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		myRestTemplate.put(destination, sup);

	}

	// DELETE /api/supers/{id}
	public void deleteSuper(int id) {

		String finalUrl = restUrl + "/" + id;
		logger.info("DELETE to " + finalUrl);

		URI destination = null;
		try {
			destination = new URI(finalUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		myRestTemplate.delete(destination);

	}

}