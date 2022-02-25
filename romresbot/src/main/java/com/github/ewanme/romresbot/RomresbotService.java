package com.github.ewanme.romresbot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RomresbotService {
	Romresbot bot;
	
	@Autowired
	public RomresbotService() {
		this.bot = new Romresbot();
	}
	
	private void login() throws IOException {
		String username = "";
		String password = "";
		
		try (InputStream input = new FileInputStream("../login.credentials")) {
			Properties prop = new Properties();
			prop.load(input);
			username = prop.getProperty("LOGIN_USER");
			password = prop.getProperty("LOGIN_PASSWORD");
		}
		catch (IOException e) {
			throw new IOException("Could not read credentials file.\n" + e.getMessage());
		}
		
		bot.openWebPage("https://tp.uio.no/ntnu/rombestilling/");
		bot.login(username, password);
	}
	
	public String reserveRoom() {
		try {
			login();
			
			Calendar date = new GregorianCalendar(2022, 3, 4, 10, 0);
			bot.createReservation("Gløshaugen", null, null, 0, null, date, 1, "Kollokvie", null);
			return bot.reserveRoom();
		}
		catch (Exception e) {
			return e.getMessage();
		}
	}
}
