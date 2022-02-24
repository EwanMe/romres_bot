package com.github.ewanme.romresbot;

import java.util.Date;

public class Reservation {
	private RoomLocation location;
	private Date date;
	private Integer duration;
	private String description;
	private String notes;

	public Reservation(RoomLocation location, Date date, int duration, String description, String notes) {
		this.location = location;
		this.date = date;
		this.duration = duration;
		this.description = description;
		this.notes = notes;
	}

	public RoomLocation getLocation() {
		return location;
	}

	public Date getDate() {
		return date;
	}

	public Integer getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}
	
	public String getNotes() {
		return notes;
	}
}
