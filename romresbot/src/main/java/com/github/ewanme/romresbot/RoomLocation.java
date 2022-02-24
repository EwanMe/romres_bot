package com.github.ewanme.romresbot;

import java.util.List;

public class RoomLocation {
	private String area;
	private String building;
	private Integer size;
	public List<String> equipment;
	
	public RoomLocation(String area, String building, int size, List<String> equipment) {
		this.area = area;
		this.building = building;
		this.size = size;
		this.equipment = equipment;
	}
	
	String getArea() {
		return area;
	}
	
	String getBulding() {
		return building;
	}
	
	Integer getSize() {
		return size;
	}
	
	List<String> getEquipment() {
		return equipment;
	}
}
