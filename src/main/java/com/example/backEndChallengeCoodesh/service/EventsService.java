package com.example.backEndChallengeCoodesh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backEndChallengeCoodesh.model.Events;
import com.example.backEndChallengeCoodesh.repository.EventsRepository;

@Service
public class EventsService {

	@Autowired
	private EventsRepository eventsRepository;

	public Events saveEvent(Events event) {
		return eventsRepository.save(event);
	}

}
