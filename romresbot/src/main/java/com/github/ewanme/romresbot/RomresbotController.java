package com.github.ewanme.romresbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/romres")
public class RomresbotController {
	
	private final RomresbotService service;
	
	@Autowired
	public RomresbotController(WebScraper scraper) {
		this.service = new RomresbotService();
	}
	
	@GetMapping
	public String helloWorld() {
		return service.getTitle();
	}
}
