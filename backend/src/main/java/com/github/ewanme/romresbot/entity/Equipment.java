package com.github.ewanme.romresbot.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Equipment {
	
	private String name;

	public Equipment(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
