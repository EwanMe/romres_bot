package com.github.ewanme.romresbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RomresbotService {
	Romresbot bot;
	
	@Autowired
	public RomresbotService() {
		this.bot = new Romresbot(null);
	}
	
	String login() {
		bot.openWebPage("https://tp.uio.no/ntnu/rombestilling/");
		return bot.login("username", "password");
	}
}
