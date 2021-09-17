package com.workshop.project;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyRunner implements CommandLineRunner {

	@Autowired
	private MyRestClient myRestClient;

	@Override
	public void run(String... args) throws Exception {

		SuperDTO[] sups = null;

		SuperDTO sup1 = new SuperDTO(0, "Spider-Man", "Good", "Peter Parker", "Male", "Human", "Marvel Comics", new Stats(90, 55, 67, 75, 74, 85));
		SuperDTO sup2 = new SuperDTO(0, "Venom", "Bad", "Eddie Brock", "Male", "Symbiote", "Anti-Venom", new Stats(75, 57, 65, 84, 86, 84));

		log.info("Started MyRunner; press enter to step through tests");

		waitKeyPress();

		sups = myRestClient.getAllSupers();
		log.info("Retrieved " + sups.length + " supers ..");
		showSupers(sups);

		waitKeyPress();

		log.info(myRestClient.getSuper(6).toString());

		waitKeyPress();

		log.info("Storing 2 new developers");
		String urlForSup1 = myRestClient.addSuper(sup1);
		String urlForSup2 = myRestClient.addSuper(sup2);
		log.info("Super 1 stored at : " + urlForSup1);
		log.info("Super 2 stored at : " + urlForSup2);

		waitKeyPress();
		
		sups = myRestClient.getAllSupers();
		log.info("Retrieved " + sups.length + " supers ..");
		showSupers(sups);

		waitKeyPress();
		
		log.info("Modifying details for super #3");
		SuperDTO sup3 = myRestClient.getSuper(3);
		log.info(sup3.toString());

		log.info("Set race to Cyborg");
		sup3.setRace("Cyborg");

		log.info("Set power statistic to 0");
		sup3.getStats().setPower(0);

		myRestClient.modifySuper(sup3, 3);
		log.info(myRestClient.getSuper(3).toString());

		waitKeyPress();
		
		log.info("Deleting super #7 and #34");
		myRestClient.deleteSuper(7);
		myRestClient.deleteSuper(34);

		sups = myRestClient.getAllSupers();
		log.info("Retrieved " + sups.length + " supers ..");
		showSupers(sups);
		
		waitKeyPress();
		
		log.info("Retrieving super who goes by the name Groot");
		showSupers(myRestClient.filterName("Groot"));

		waitKeyPress();
		
		log.info("Retrieving super who goes by the name Scarlet Witch");
		showSupers(myRestClient.filterName("Scarlet Witch"));

		waitKeyPress();

		log.info("Retrieving supers with the alignment of bad");
		showSupers(myRestClient.filterAlignment("bad"));

		waitKeyPress();
		
		log.info("Retrieving super who goes by the full name Johnny Storm");
		showSupers(myRestClient.filterFullName("Johnny Storm"));

		waitKeyPress();

		log.info("Retrieving supers who are female");
		showSupers(myRestClient.filterGender("female"));

		waitKeyPress();
		
		log.info("Retrieving supers who are good and male");
		showSupers(myRestClient.filterAlignmentAndGender("good","male"));

		waitKeyPress();

		log.info("Retrieving supers who are of the race mutant");
		showSupers(myRestClient.filterRace("mutant"));

		waitKeyPress();

		log.info("Retrieving supers who are created by the publisher Marvel Comics");
		showSupers(myRestClient.filterPublisher("Marvel Comics"));

		waitKeyPress();

		log.info("Showing a total of 3 supers starting from super #10");
		showSupers(myRestClient.ResultPagination(3, 10));

		waitKeyPress();

		sups = myRestClient.getAllSupers();
		log.info("Retrieved " + sups.length + " supers ..");
		showSupers(sups);

		log.info("Application complete !");
	}

	private void showSupers(SuperDTO[] sups) {
		for (SuperDTO sup : sups) {
			log.info(sup.toString());
		}
	}

	private void waitKeyPress() {
		log.info("Press enter to continue ....");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		log.info("\n");
		// scanner.close();
	}
}

