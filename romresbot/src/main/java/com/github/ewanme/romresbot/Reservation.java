package com.github.ewanme.romresbot;

import java.util.Calendar;

public class Reservation {
	private RoomLocation location;
	private Calendar date;
	private Integer duration;
	private String description;
	private String notes;

	public Reservation(RoomLocation location, Calendar date, int duration, String description, String notes) {
		this.location = location;
		this.date = date;
		this.duration = duration;
		this.description = description;
		this.notes = notes;
	}

	public RoomLocation getLocation() {
		return location;
	}

	public Calendar getDate() {
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
