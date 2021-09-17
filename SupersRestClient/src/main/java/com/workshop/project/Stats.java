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

public class Stats {

	private Integer intelligence;
	private Integer strength;
	private Integer speed;
	private Integer durability;
	private Integer power;
	private Integer combat;

	public Stats(int intelligence, int strength, int speed, int durability, int power, int combat) {
		
		this.intelligence = intelligence;
		this.strength = strength;
		this.speed = speed;
		this.durability = durability;
		this.power = power;
		this.combat = combat;

	}

}
