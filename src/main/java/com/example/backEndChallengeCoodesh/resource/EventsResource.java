package com.example.backEndChallengeCoodesh.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backEndChallengeCoodesh.model.Events;
import com.example.backEndChallengeCoodesh.repository.EventsRepository;
import com.example.backEndChallengeCoodesh.service.EventsService;

@RestController
@RequestMapping("/events")
public class EventsResource {

	@Autowired
	private EventsRepository eventsRepository;

	@Autowired
	private EventsService eventsService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Events> listEvents() {
		return eventsRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Events> saveEvent(@Valid @RequestBody Events event) {
		Events eventSaved = eventsService.saveEvent(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(eventSaved);
	}

}
