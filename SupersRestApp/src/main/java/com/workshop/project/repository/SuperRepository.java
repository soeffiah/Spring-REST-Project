package com.workshop.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.workshop.project.model.Super;

public interface SuperRepository extends PagingAndSortingRepository<Super, Integer> {
  
  // Returns the last row in the table
  @Query(value = "SELECT * FROM supers ORDER BY id DESC LIMIT 1", 
      nativeQuery = true)
  List<Super> getNewlyAddedRecord();
  
  // Update existing row in the table by its id
  @Modifying
  @Transactional
  @Query(value = "UPDATE supers SET name = :supName, alignment = :supAlignment, full_name = :supFullName, gender = :supGender, race = :supRace, publisher = :supPublisher, intelligence = :supIntelligence, strength = :supStrength, speed = :supSpeed, durability = :supDurability, power = :supPower, combat = :supCombat WHERE id = :supId", 
      nativeQuery = true)
  
  void updateExistingRecord(@Param("supId") int supId, @Param("supName") String supName, @Param("supAlignment") String supAlignment, @Param("supFullName") String supFullName, @Param("supGender") String supGender, @Param("supRace") String supRace, @Param("supPublisher") String supPublisher, @Param("supIntelligence") int supIntelligence, @Param("supStrength") int supStrength, @Param("supSpeed") int supSpeed, @Param("supDurability") int supDurability, @Param("supPower") int supPower, @Param("supCombat") int supCombat); 
  
  
  
  // Find a super by specifying their name
  List<Super> findByName(String name);
  
  // Find all supers that are of a specific alignment (good/bad)
  List<Super> findByAlignment(String alignment);
  
  // Find a super by specifying their full name
  List<Super> findByFullName(String fullName);
  
  // Find all supers that are of a specific gender (male/female)
  List<Super> findByGender(String gender);
  
  // Find all supers that are of a specific race
  List<Super> findByRace(String race);
  
  // Find all supers that were created by a specific publisher
  List<Super> findByPublisher(String publisher);
  
  // Find all supers who are of a specific alignment and gender
  List<Super> findByAlignmentAndGender(String alignment, String gender);
  
}