package com.workshop.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SuperDTO {

	private Integer id;
	
	private String name;
	private String alignment;
	private String fullName;
	private String gender;
	private String race;
	private String publisher;
	
	private Stats stats;

}
