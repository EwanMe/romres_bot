package com.github.ewanme.romresbot;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RomresbotService {
	Romresbot bot;
	
	@Autowired
	public RomresbotService() {
		this.bot = new Romresbot();
	}
	
	private void login(String username, String password) {
		bot.openWebPage("https://tp.uio.no/ntnu/rombestilling/");
		bot.login(username, password);
	}
	
	public void createReservation(String area, String building, String type, int size, List<String> equipment, Calendar date, int duration, String description, String notes) {
		bot.createReservation(area, building, type, size, equipment, date, duration, description, notes);
	}
	
	public Boolean reserveRoom(String username, String password) {
		try {
			login(username, password);
			return bot.reserveRoom();
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}
