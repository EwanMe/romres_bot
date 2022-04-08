package com.github.ewanme.romresbot;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	public ResponseEntity<String> reserve(@RequestHeader("Authorization") String auth, @RequestBody String body) {
		JsonParser parser = JsonParserFactory.getJsonParser();
		Map<String, Object> data = parser.parseMap(body);

		// Parse date and time as one Calendar object.
		String[] dateStrings = ((String) data.get("date")).split("-");
		String[] timeStrings = ((String) data.get("time")).split(":");
		Calendar date = new GregorianCalendar(Integer.parseInt(dateStrings[0]), Integer.parseInt(dateStrings[0]),
				Integer.parseInt(dateStrings[0]), Integer.parseInt(timeStrings[0]), Integer.parseInt(timeStrings[1]));
		
		// Parse rest of mappings.
		String area = (String) data.get("area");
		String building = (String) data.get("building");
		String type = (String) data.get("type");
		Integer size = (Integer) data.get("size");
		List<String> equipment = (List<String>) data.get("equipment");
		Integer duration = Integer.parseInt((String) data.get("duration"));
		String description = (String) data.get("description");
		String notes = (String) data.get("notes");

		service.createReservation(area, building, type, size, equipment, date, duration, description, notes);

		if (auth != null && auth.toLowerCase().startsWith("basic")) {
			String base64Credentials = auth.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			final String[] values = credentials.split(":", 2);

			if (service.reserveRoom(values[0], values[1])) {			
				return new ResponseEntity<>("Room reserved!", HttpStatus.OK);
			}
			
			return new ResponseEntity<>("Could not reserve room.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("Authorization not reckognized.", HttpStatus.UNAUTHORIZED);
	}
}
