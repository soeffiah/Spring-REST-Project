package com.workshop.project.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workshop.project.dto.SuperDTO;
import com.workshop.project.exception.IncorrectJSONFormatException;
import com.workshop.project.exception.IncorrectURLFormatException;
import com.workshop.project.model.Super;
import com.workshop.project.repository.SuperRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Slf4j
@Service
public class SuperService {

	@Autowired
	private SuperRepository supRepo;

	private static final String NAME_PARAM = "name";
	private static final String ALIGNMENT_PARAM = "alignment";
	private static final String FULLNAME_PARAM = "full name";
	private static final String GENDER_PARAM = "gender";
	private static final String RACE_PARAM = "race";
	private static final String PUBLISHER_PARAM = "publisher";

	private static final String LIMIT_PARAM = "limit";
	private static final String PAGE_PARAM = "page";

	public List<SuperDTO> getAllSupers() {

		List<SuperDTO> mySupers = new ArrayList<SuperDTO>();
		Iterable<Super> supList = supRepo.findAll();
		log.info("Retrieved developers from database");
		for (Super sup : supList) {
			mySupers.add(new SuperDTO(sup));
		}

		return mySupers;
	}

	public int saveSuper(SuperDTO supDTO) {

		if (isSuperValid(supDTO)) {
			Super newSup = new Super(supDTO);
			supRepo.save(newSup);
		} else
			throw new IncorrectJSONFormatException("JSON is valid, but incorrect format for super");

		List<Super> sups = supRepo.getNewlyAddedRecord();
		int newSupId = sups.get(0).getId();
		return newSupId;

	}

	public void updateSuper(int supId, SuperDTO supDTO) {

		if (isSuperValid(supDTO)) {
			Super newSup = new Super(supDTO);
			if (!supRepo.existsById(supId))
				throw new IncorrectURLFormatException("Super with id : " + supId + " does not exist");

			log.info("Updating super id : " + supId);
			log.info("With new contents : " + newSup.toString());

			supRepo.updateExistingRecord(supId, newSup.getName(), newSup.getAlignment(), newSup.getFullName(),
					newSup.getGender(), newSup.getRace(), newSup.getPublisher(), newSup.getIntelligence(),
					newSup.getStrength(), newSup.getSpeed(), newSup.getDurability(), newSup.getPower(),
					newSup.getCombat());
		} else
			throw new IncorrectJSONFormatException("JSON is valid, but incorrect format for super");

	}

	public void deleteSuper(int supId) {

		if (!supRepo.existsById(supId))
			throw new IncorrectURLFormatException("Super with id : " + supId + " does not exist");

		log.info("Deleting super id : " + supId);

		supRepo.deleteById(supId);

	}

	public SuperDTO getSingleSuper(int supId) {

		if (!supRepo.existsById(supId))
			throw new IncorrectURLFormatException("Super with id : " + supId + " does not exist");

		Optional<Super> sup = supRepo.findById(supId);
		return new SuperDTO(sup.get());

	}

	public List<SuperDTO> getWithParams(Map<String, String> allParams) {

		String nameValue = null;
		String alignmentValue = null;
		String fullNameValue = null;
		String genderValue = null;
		String raceValue = null;
		String publisherValue = null;

		int pageVal = -1;
		int limitVal = -1;

		Pageable paging;

		List<SuperDTO> listToReturn = new ArrayList<SuperDTO>();

		if (allParams.size() == 0)
			return getAllSupers();
		else {

			for (Map.Entry<String, String> entry : allParams.entrySet()) {

				String tempKey = decodeValue(entry.getKey());
				String tempValue = decodeValue(entry.getValue());
				log.info(tempKey + " : " + tempValue);

				if (tempKey.equals(NAME_PARAM)) {
					nameValue = tempValue;
				} else if (tempKey.equals(ALIGNMENT_PARAM)) {
					alignmentValue = tempValue;
					if (!(alignmentValue.toLowerCase().equals("good") || alignmentValue.toLowerCase().equals("bad")))
						throw new IncorrectURLFormatException(ALIGNMENT_PARAM + " must be either good or bad");
				} else if (tempKey.equals(FULLNAME_PARAM)) {
					fullNameValue = tempValue;
				} else if (tempKey.equals(GENDER_PARAM)) {
					genderValue = tempValue;
					if (!(genderValue.toLowerCase().equals("male") || genderValue.toLowerCase().equals("female")))
						throw new IncorrectURLFormatException(GENDER_PARAM + " must be either male or female");
				} else if (tempKey.equals(RACE_PARAM)) {
					raceValue = tempValue;
				} else if (tempKey.equals(PUBLISHER_PARAM)) {
					publisherValue = tempValue;
				} else if (tempKey.equals(LIMIT_PARAM)) {
					try {
						limitVal = Integer.parseInt(tempValue);
					} catch (NumberFormatException ex) {
						throw new IncorrectURLFormatException("Limit parameter must be a number");
					}

					if (limitVal < 0)
						throw new IncorrectURLFormatException("Limit parameter must be a positive number");

				} else if (tempKey.equals(PAGE_PARAM)) {
					try {
						pageVal = Integer.parseInt(tempValue);
					} catch (NumberFormatException ex) {
						throw new IncorrectURLFormatException("Page parameter must be a number");
					}

					if (pageVal < 0)
						throw new IncorrectURLFormatException("Page parameter must be a positive number");

				}
			}

			if (limitVal >= 0 && pageVal >= 0) {

				log.info("Retrieving supers from page : " + pageVal + " with a limit of : " + limitVal);

				paging = PageRequest.of(pageVal, limitVal);
				List<Super> sups = supRepo.findAll(paging).getContent();
				listToReturn = convertSuperListToSuperDTOList(sups);

			} else if (!(limitVal < 0 && pageVal < 0)) {

				throw new IncorrectURLFormatException("Both limit and page parameters must be supplied together");

			}

			if (alignmentValue != null && genderValue != null) {

				log.info("Retrieving all supers with the alignment : " + alignmentValue + " and are of gender : "
						+ genderValue);
				listToReturn = convertSuperListToSuperDTOList(
						supRepo.findByAlignmentAndGender(alignmentValue, genderValue));

			} else if (nameValue != null) {
				log.info("Retrieving super that goes by the name of : " + nameValue);
				listToReturn = convertSuperListToSuperDTOList(supRepo.findByName(nameValue));

			} else if (alignmentValue != null) {
				log.info("Retrieving all supers with the alignment of : " + alignmentValue);
				listToReturn = convertSuperListToSuperDTOList(supRepo.findByAlignment(alignmentValue));

			} else if (fullNameValue != null) {
				log.info("Retrieving super that goes by the full name of : " + fullNameValue);
				listToReturn = convertSuperListToSuperDTOList(supRepo.findByFullName(fullNameValue));

			} else if (genderValue != null) {
				log.info("Retrieving all supers of the gender : " + genderValue);
				listToReturn = convertSuperListToSuperDTOList(supRepo.findByGender(genderValue));

			} else if (raceValue != null) {
				log.info("Retrieving all supers of the race : " + raceValue);
				listToReturn = convertSuperListToSuperDTOList(supRepo.findByRace(raceValue));

			} else if (publisherValue != null) {
				log.info("Retrieving all supers created by the publisher : " + publisherValue);
				listToReturn = convertSuperListToSuperDTOList(supRepo.findByPublisher(publisherValue));
			}

			return listToReturn;

		}

	}

	private boolean isSuperValid(SuperDTO supDTO) {

		return (supDTO.getName() != null
				&& (supDTO.getAlignment().toLowerCase().equals("good") || supDTO.getAlignment().toLowerCase().equals("bad"))
				&& supDTO.getFullName() != null
				&& (supDTO.getGender().toLowerCase().equals("male") || supDTO.getGender().toLowerCase().equals("female"))
				&& supDTO.getRace() != null && supDTO.getPublisher() != null && supDTO.getStats() != null);

	}

	private List<SuperDTO> convertSuperListToSuperDTOList(Iterable<Super> supList) {

		List<SuperDTO> mySupers = new ArrayList<SuperDTO>();
		for (Super sup : supList) {
			mySupers.add(new SuperDTO(sup));
		}

		return mySupers;

	}

	// Decodes a URL-encoded string using `UTF-8`
	private String decodeValue(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}

}
