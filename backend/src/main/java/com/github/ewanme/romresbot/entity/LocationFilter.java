package com.github.ewanme.romresbot.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LocationFilter implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Integer id;
	
	private String area;
	
	private String building;
	
	private String type;
	
	private Integer roomSize;
	
	@ElementCollection
	private List<Equipment> equipment;
	
	public LocationFilter() {}
	
	public LocationFilter(String area, String building, String type, int size, List<Equipment> equipment) {
		this.area = area;
		this.building = building;
		this.type = type;
		this.roomSize = size;
		this.equipment = equipment;
	}
	
	public String getArea() {
		return area;
	}
	
	public String getBulding() {
		return building;
	}
	
	public String getType() {
		return type;
	}
	
	public Integer getRoomSize() {
		return roomSize;
	}
	
	public List<Equipment> getEquipment() {
		return equipment;
	}
}
