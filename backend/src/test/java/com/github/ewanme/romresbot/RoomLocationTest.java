package com.github.ewanme.romresbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.ewanme.romresbot.entity.Equipment;
import com.github.ewanme.romresbot.entity.LocationFilter;

public class RoomLocationTest {
	
	@Test
	public void testRoomLocationCreation() {
		String area = "Gløshaugen";
		String building = "Kjelhuset";
		String roomType = "Grupperom";
		int size = 1;
		List<Equipment> equipment = new ArrayList<Equipment>();
		equipment.add(new Equipment("DVD-spiller"));
		equipment.add(new Equipment("Projektor"));
		
		
		LocationFilter loc = new LocationFilter(area, building, roomType, size, equipment);
		
		assertEquals(area, loc.getArea());
		assertEquals(building, loc.getBulding());
		assertEquals(roomType, loc.getType());
		assertEquals(size, loc.getRoomSize());
		assertEquals(equipment, loc.getEquipment());
	}

}
