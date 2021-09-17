package com.workshop.project.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.workshop.project.dto.CustomErrorMessage;
import com.workshop.project.exception.IncorrectJSONFormatException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class SuperControllerExceptionHandler {

  
  private static final String urlToConsult = "https://www.paynet.my/support/api/docs";

  
  // Exception handler for incorrect JSON format
  @ExceptionHandler({IncorrectJSONFormatException.class, IncorrectURLFormatException.class}) 
  public ResponseEntity<CustomErrorMessage> handleCustomException(RuntimeException rte) {
    
    log.info("Exception handling logic to handle incorrect JSON or URL format");

    HttpStatus statusToReturn = HttpStatus.BAD_REQUEST;
    String messageToReturn = rte.getMessage();

    // Returns additional (more specific) information
    int localErrorId = 1000;
    
    CustomErrorMessage newMessage = new CustomErrorMessage(new Date(), statusToReturn.value(), statusToReturn.toString(), messageToReturn, localErrorId, urlToConsult);
    
    return new ResponseEntity<CustomErrorMessage>(newMessage, statusToReturn);
  }
    
  
  // Exception handler for Spring framework exceptions
  @ExceptionHandler(HttpMessageNotReadableException.class) 
  public ResponseEntity<CustomErrorMessage> handleSpringFrameworkException (HttpMessageNotReadableException mnrex) {
    
    log.info("Exception handling logic to handle Spring framework exceptions");

    HttpStatus statusToReturn = HttpStatus.BAD_REQUEST;
    String messageToReturn = "Your JSON was malformed. Please check your JSON and try again.";

    // Returns additional (more specific) information
    int localErrorId = 1100;
    
    CustomErrorMessage newMessage = new CustomErrorMessage(new Date(), statusToReturn.value(), statusToReturn.toString(), messageToReturn, localErrorId, urlToConsult);
    
    return new ResponseEntity<CustomErrorMessage>(newMessage, statusToReturn);    
    
  }
  
  
}
