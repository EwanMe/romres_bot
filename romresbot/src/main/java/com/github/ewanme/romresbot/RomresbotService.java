package com.github.ewanme.romresbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RomresbotService {
	WebScraper scraper;
	
	@Autowired
	public RomresbotService() {
		this.scraper = new WebScraper();
	}
	
	String getTitle() {
		return scraper.openWebPage();
	}
}
