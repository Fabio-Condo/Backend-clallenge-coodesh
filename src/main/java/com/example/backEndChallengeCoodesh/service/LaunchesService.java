package com.example.backEndChallengeCoodesh.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backEndChallengeCoodesh.model.Launches;
import com.example.backEndChallengeCoodesh.repository.LaunchesRepository;

@Service
@Transactional
public class LaunchesService {

	@Autowired
	private LaunchesRepository launchesRepository;

	public Launches saveLaunch(Launches launch) {
		launch.setId(UUID.randomUUID());
		return launchesRepository.save(launch);
	}
}
