package com.example.backEndChallengeCoodesh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backEndChallengeCoodesh.model.Articles;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
	public Page<Articles> findByTitleContaining(String title, Pageable pageable);
	public Articles findByTitle(String title);
}
