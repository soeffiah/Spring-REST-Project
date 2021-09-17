package com.workshop.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppStartupRunner implements CommandLineRunner {


  @Override
  public void run(String... args) throws Exception {
    log.info("Startup logic in CommandLineRunner implementation for SupersRestApp");
  }
    
}