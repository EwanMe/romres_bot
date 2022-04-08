package com.github.ewanme.romresbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RoomLocationTest {
	
	@Test
	public void testRoomLocationCreation() {
		String area = "Gløshaugen";
		String building = "Kjelhuset";
		String roomType = "Grupperom";
		int size = 1;
		List<String> equipment = new ArrayList<String>(Arrays.asList("DVD-spiller", "Projektor"));
		
		
		RoomLocation loc = new RoomLocation(area, building, roomType, size, equipment);
		
		assertEquals(area, loc.getArea());
		assertEquals(building, loc.getBulding());
		assertEquals(roomType, loc.getType());
		assertEquals(size, loc.getSize());
		assertEquals(equipment, loc.getEquipment());
	}

}
