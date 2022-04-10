package com.github.ewanme.romresbot;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class RoomLocation {
	private String area;
	private String building;
	private String type;
	private Integer size;
	private List<String> equipment;
	
	public RoomLocation(String area, String building, String type, int size, List<String> equipment) {
		this.area = area;
		this.building = building;
		this.type = type;
		this.size = size;
		this.equipment = equipment;
	}
	
	String getArea() {
		return area;
	}
	
	String getBulding() {
		return building;
	}
	
	String getType() {
		return type;
	}
	
	Integer getSize() {
		return size;
	}
	
	List<String> getEquipment() {
		return equipment;
	}
}
