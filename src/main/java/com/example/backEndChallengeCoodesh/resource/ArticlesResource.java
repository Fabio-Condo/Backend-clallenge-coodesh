package com.example.backEndChallengeCoodesh.resource;

import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backEndChallengeCoodesh.exception.ArticleNotFoundException;
import com.example.backEndChallengeCoodesh.exception.ExceptionHandling;
import com.example.backEndChallengeCoodesh.model.Articles;
import com.example.backEndChallengeCoodesh.model.HttpResponse;
import com.example.backEndChallengeCoodesh.repository.ArticlesRepository;
import com.example.backEndChallengeCoodesh.service.ArticlesService;

@RestController
@RequestMapping(path = { "/", "" })
public class ArticlesResource extends ExceptionHandling {

	public static final String ARTICLE_DELETED_SUCCESSFULLY = "Arcle deleted successfully";
	public static final String MESSAGE = "Back-end Challenge 2021 🏅 - Space Flight News";

	@Autowired
	private ArticlesService articlesService;

	@Autowired
	private ArticlesRepository articlesRepository;

	@GetMapping
	private ResponseEntity<String> message() {
		return ResponseEntity.status(HttpStatus.OK).body(MESSAGE);
	}

	@GetMapping("/articles")
	@ResponseStatus(HttpStatus.OK)
	public Page<Articles> listArticles(@RequestParam(required = false, defaultValue = "") String title,
			Pageable pageable) {
		return articlesRepository.findByTitleContaining(title, pageable);
	}

	@PostMapping("/articles")
	public ResponseEntity<Articles> saveArticle(@Valid @RequestBody Articles article) {
		Articles articleSaved = articlesService.saveArticle(article);
		return ResponseEntity.status(HttpStatus.CREATED).body(articleSaved);
	}

	@GetMapping("/articles/{id}")
	public ResponseEntity<?> getSingleArticle(@PathVariable("id") Long id) throws ArticleNotFoundException {
		Articles article = articlesService.getExistArticle(id);
		return ResponseEntity.status(HttpStatus.OK).body(article);

	}

	@PutMapping("/articles/{id}")
	public ResponseEntity<Articles> updateArticle(@PathVariable Long id, @RequestBody Articles article)
			throws ArticleNotFoundException {
		Articles articleSaved = articlesService.updateArticle(id, article);
		return ResponseEntity.status(HttpStatus.OK).body(articleSaved);
	}

	@DeleteMapping("/articles/{id}")
	public ResponseEntity<HttpResponse> deletar(@PathVariable("id") Long id) throws ArticleNotFoundException {
		articlesService.deleteArticle(id);
		return response(OK, ARTICLE_DELETED_SUCCESSFULLY);
	}

	@PutMapping("/articles/{id}/featured")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePropertyFeatured(@PathVariable Long id, @RequestBody Boolean featured)
			throws ArticleNotFoundException {
		articlesService.updatePropertyFeatured(id, featured);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(
				new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message),
				httpStatus);
	}

}
