package com.example.backEndChallengeCoodesh.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backEndChallengeCoodesh.exception.ArticleNotFoundException;
import com.example.backEndChallengeCoodesh.model.Articles;
import com.example.backEndChallengeCoodesh.repository.ArticlesRepository;

@Service
public class ArticlesService {

	public static final String NO_ARTICLE_FOUND_BY_ID = "No article found by id: ";

	@Autowired
	private ArticlesRepository articlesRepository;

	public Articles getExistArticle(Long id) throws ArticleNotFoundException {
		Articles articleSaved = articlesRepository.findById(id)
				.orElseThrow(() -> new ArticleNotFoundException(NO_ARTICLE_FOUND_BY_ID + id));
		return articleSaved;
	}

	public Articles saveArticle(Articles article) {
		return articlesRepository.save(article);
	}

	public Articles updateArticle(Long id, Articles article) throws ArticleNotFoundException {
		Articles articleSaved = getExistArticle(id);
		BeanUtils.copyProperties(article, articleSaved, "id");
		return articlesRepository.save(articleSaved);
	}

	public void updatePropertyFeatured(Long id, Boolean featured) throws ArticleNotFoundException {
		Articles articleSaved = getExistArticle(id);
		articleSaved.setFeatured(featured);
		articlesRepository.save(articleSaved);
	}

	public void deleteArticle(Long id) throws ArticleNotFoundException {
		getExistArticle(id);
		articlesRepository.deleteById(id);
	}
}
