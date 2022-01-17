package com.example.backEndChallengeCoodesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backEndChallengeCoodesh.model.Events;

public interface EventsRepository extends JpaRepository<Events, Long> {

}
