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

import com.example.backEndChallengeCoodesh.model.Launches;
import com.example.backEndChallengeCoodesh.repository.LaunchesRepository;
import com.example.backEndChallengeCoodesh.service.LaunchesService;

@RestController
@RequestMapping("/launches")
public class LaunchesResource {

	@Autowired
	private LaunchesService launchesService;

	@Autowired
	private LaunchesRepository launchesRepository;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Launches> listLaunches() {
		return launchesRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Launches> saveLaunch(@Valid @RequestBody Launches launche) {
		Launches launcheSaved = launchesService.saveLaunch(launche);
		return ResponseEntity.status(HttpStatus.CREATED).body(launcheSaved);
	}

}
