package com.workshop.project.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestParam;

import com.workshop.project.service.SuperService;
import com.workshop.project.dto.SuperDTO;
import com.workshop.project.exception.IncorrectURLFormatException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class SuperController {
  
  @Autowired
  SuperService supService;
  
  
  @GetMapping("/supers")
  public List<SuperDTO> getSupers(@RequestParam Map<String, String> allParams) {

    log.info("GET /api/supers invoked");
    
    return supService.getWithParams(allParams);
  }
  
  @PostMapping("/supers")
  public ResponseEntity<Object> addSingleSuper(@RequestBody SuperDTO supDTO) {
    
    log.info("POST /api/supers invoked to");
    
    int supId = supService.saveSuper(supDTO);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(supId)
        .toUri();   
    return ResponseEntity.created(location).build();
    
  }
  
  @PutMapping("/supers/{supIdString}")
  public void updateSuper(@PathVariable String supIdString, @RequestBody SuperDTO supDTO) {

    log.info("PUT /api/supers/" + supIdString + " invoked");
    
    int supId = 0;
    try {
      supId = Integer.parseInt(supIdString);
    } catch (NumberFormatException ex) {
      throw new IncorrectURLFormatException("Super ID specified in path must be a number");
    }
    supService.updateSuper(supId, supDTO);

  }
  
  @DeleteMapping("/supers/{supIdString}")
  public void deleteSuper(@PathVariable String supIdString) {

    log.info("DELETE /api/supers/" + supIdString + " invoked");
    
    int supId = 0;
    try {
      supId = Integer.parseInt(supIdString);
    } catch (NumberFormatException ex) {
      throw new IncorrectURLFormatException("Super ID specified in path must be a number");
    }
    supService.deleteSuper(supId);

  }
  
  @GetMapping("/supers/{supIdString}")
  public SuperDTO getSingleSuper(@PathVariable String supIdString) {

    log.info("GET /api/supers/" + supIdString + " invoked");

    Integer supId = null;
    try {
      supId = Integer.parseInt(supIdString);
    } catch (NumberFormatException ex) {
      throw new IncorrectURLFormatException("Super id specified in path must be a number");
    }
    return (supService.getSingleSuper(supId));

  }
  

}
