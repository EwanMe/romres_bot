package com.github.ewanme.romresbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/romres")
public class RomresbotController {
	
	private final RomresbotService service;
	
	@Autowired
	public RomresbotController(RomresbotService service) {
		this.service = new RomresbotService();
	}
	
	@GetMapping
	public String login() {
		return service.reserveRoom();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String reserve(@RequestHeader("Authorization") String auth) {
		System.out.println(auth);
		return "Oki";
	}
}
