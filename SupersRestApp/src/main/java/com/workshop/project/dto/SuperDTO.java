package com.workshop.project.dto;

import com.workshop.project.model.Super;

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

	public SuperDTO(Super sup) {
		
		this.id = sup.getId();
		this.name = sup.getName();
		this.alignment = sup.getAlignment();
		this.fullName = sup.getFullName();
		this.gender = sup.getGender();
		this.race = sup.getRace();
		this.publisher = sup.getPublisher();
		
		this.stats = new Stats(sup.getIntelligence(), sup.getStrength(), sup.getSpeed(), sup.getDurability(), sup.getPower(), sup.getCombat());

	}

}
