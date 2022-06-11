package com.github.ewanme.romresbot.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Reservation implements Serializable {
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "locationId")
	private LocationFilter location;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	private Integer duration;
	
	private String description;
	
	private String notes;
	
	public Reservation() {}

	public Reservation(LocationFilter location, Calendar date, int duration, String description, String notes) {
		this.location = location;
		this.date = date;
		this.duration = duration;
		this.description = description;
		this.notes = notes;
	}

	public LocationFilter getLocation() {
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
