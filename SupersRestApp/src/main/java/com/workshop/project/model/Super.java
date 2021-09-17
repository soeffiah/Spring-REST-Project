package com.workshop.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workshop.project.dto.SuperDTO;

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

@Entity
@Table(name = "supers")
public class Super {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String alignment;
	private String fullName;
	private String gender;
	private String race;
	private String publisher;

	private Integer intelligence;
	private Integer strength;
	private Integer speed;
	private Integer durability;
	private Integer power;
	private Integer combat;

	public Super(SuperDTO supDTO) {

		this.name = supDTO.getName();
		this.alignment = supDTO.getAlignment();
		this.fullName = supDTO.getFullName();
		this.gender = supDTO.getGender();
		this.race = supDTO.getRace();
		this.publisher = supDTO.getPublisher();

		this.intelligence = supDTO.getStats().getIntelligence();
		this.strength = supDTO.getStats().getStrength();
		this.speed = supDTO.getStats().getSpeed();
		this.durability = supDTO.getStats().getDurability();
		this.power = supDTO.getStats().getPower();
		this.combat = supDTO.getStats().getCombat();

	}

}
