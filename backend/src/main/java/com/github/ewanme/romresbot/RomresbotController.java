package com.github.ewanme.romresbot;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
//	@GetMapping	
//	public String login() {
//		return service.reserveRoom();
//	}
	
	@PostMapping
	@CrossOrigin(origins = "http://localhost:3000")
	public String reserve(@RequestHeader("Authorization") String auth) {
		if (auth != null && auth.toLowerCase().startsWith("basic")) {
			String base64Credentials = auth.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			final String[] values = credentials.split(":", 2);

			service.reserveRoom(values[0], values[1]);
		}
		
		return "Oki";
	}
}
