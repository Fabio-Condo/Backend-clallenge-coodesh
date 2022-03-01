package com.example.backEndChallengeCoodesh.service;

import static com.example.backEndChallengeCoodesh.constant.FileConstant.DIRECTORY_CREATED;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.DOT;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.FILE_SAVED_IN_FILE_SYSTEM;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.FORWARD_SLASH;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.JPG_EXTENSION;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.NOT_AN_IMAGE_FILE;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.DEFAULT_ARTICLE_IMAGE_PATH;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.ARTICLE_FOLDER;
import static com.example.backEndChallengeCoodesh.constant.FileConstant.ARTICLE_IMAGE_PATH;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.backEndChallengeCoodesh.model.Articles;
import com.example.backEndChallengeCoodesh.model.Events;
import com.example.backEndChallengeCoodesh.model.Launches;
import com.example.backEndChallengeCoodesh.repository.ArticlesRepository;
import com.example.backEndChallengeCoodesh.exception.*;

@Service
public class ArticlesService {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass()); 

	public static final String NO_ARTICLE_FOUND_BY_ID = "No article found by id: ";
	public static final String TITLE_ARTICLE_FOUND = "Already existe a article  with this title: ";

	@Autowired
	private ArticlesRepository articlesRepository;

	public Articles getExistArticle(Long id) throws ArticleNotFoundException {
		Articles articleSaved = articlesRepository.findById(id)
				.orElseThrow(() -> new ArticleNotFoundException(NO_ARTICLE_FOUND_BY_ID + id));
		return articleSaved;
	}
	
	public Articles getExistArticleByTitle(String title) throws ArticleNotFoundException, ExistArticleTitleException {
		Articles articleSaved = articlesRepository.findByTitle(title);
		if(title != null) {
			throw new ExistArticleTitleException(TITLE_ARTICLE_FOUND + title);
		}
		return articleSaved;
	}

<<<<<<< HEAD
    public Articles saveArticle(String title, String newsSite, String summary, MultipartFile articleImage, Boolean featured, Long eventId, UUID launchId) throws IOException, NotAnImageFileException, ArticleNotFoundException, ExistArticleTitleException {
    	getExistArticleByTitle(title);
    	Articles article = new Articles();
        Events event = new Events();
        event.setId(eventId);
        Launches launch = new Launches();
        launch.setId(launchId);
        article.setEvents(event);
        article.setLaunches(launch);
        article.setTitle(title);
        article.setNewsSite(newsSite);
        article.setSummary(summary);
        article.setFeatured(featured);
        article.setPublishedAt(new Date());
        article.setUpdatedAt(new Date());
        article.setImageUrl(getTemporaryProfileImageUrl(title)); 
        articlesRepository.save(article);
        saveProfileImage(article, articleImage);
        LOGGER.info("New article: " + title);
        return article;
    }
    
    public Articles updateArticle(Long id, String title, String newsSite, String summary, MultipartFile articleImage, Boolean featured, Long eventId, UUID launchId) throws IOException, NotAnImageFileException, ArticleNotFoundException, ExistArticleTitleException {
    	getExistArticle(id);
    	Articles currentArticle = getExistArticle(id);
        Events event = new Events();
        event.setId(eventId);
        Launches launch = new Launches();
        launch.setId(launchId);
        currentArticle.setEvents(event);
        currentArticle.setLaunches(launch);
        currentArticle.setTitle(title);
        currentArticle.setNewsSite(newsSite);
        currentArticle.setSummary(summary);
        currentArticle.setFeatured(featured);
        currentArticle.setUpdatedAt(new Date());
        currentArticle.setImageUrl(getTemporaryProfileImageUrl(title)); 
        articlesRepository.save(currentArticle);
        saveProfileImage(currentArticle, articleImage);
        LOGGER.info("New article: " + title);
        return currentArticle;
    }
=======
        public Articles saveArticle(String title, String newsSite, String summary, MultipartFile articleImage, Boolean featured, Long eventId, UUID launchId) throws IOException, NotAnImageFileException, ArticleNotFoundException, ExistArticleTitleException {
		getExistArticleByTitle(title);
		Articles article = new Articles();
		Events event = new Events();
		event.setId(eventId);
		Launches launch = new Launches();
		launch.setId(launchId);
		article.setEvents(event);
		article.setLaunches(launch);
		article.setTitle(title);
		article.setNewsSite(newsSite);
		article.setSummary(summary);
		article.setFeatured(featured);
		article.setPublishedAt(new Date());
		article.setUpdatedAt(new Date());
		article.setImageUrl(getTemporaryProfileImageUrl(title)); 
		articlesRepository.save(article);
		saveProfileImage(article, articleImage);
		LOGGER.info("New article: " + title);
		return article;
        }

	public Articles updateArticle(Long id, Articles article) throws ArticleNotFoundException {
		Articles articleSaved = getExistArticle(id);
		BeanUtils.copyProperties(article, articleSaved, "id");
		return articlesRepository.save(articleSaved);
	}
>>>>>>> 957c8602cebdf62c99713f14363086d916e9172b

	public void updatePropertyFeatured(Long id, Boolean featured) throws ArticleNotFoundException {
		Articles articleSaved = getExistArticle(id);
		articleSaved.setFeatured(featured);
		articlesRepository.save(articleSaved);
	}

	public void deleteArticle(Long id) throws ArticleNotFoundException {
		getExistArticle(id);
		articlesRepository.deleteById(id);
	}
	
    private String getTemporaryProfileImageUrl(String title) {  
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_ARTICLE_IMAGE_PATH + title).toUriString();   
    }
	
    private void saveProfileImage(Articles article, MultipartFile image) throws IOException, NotAnImageFileException {
        if (image != null) {
            if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(image.getContentType())) {
                throw new NotAnImageFileException(image.getOriginalFilename() + NOT_AN_IMAGE_FILE);
            }
            Path articleFolder = Paths.get(ARTICLE_FOLDER + article.getTitle()).toAbsolutePath().normalize();  
            if(!Files.exists(articleFolder)) {   
                Files.createDirectories(articleFolder);
                LOGGER.info(DIRECTORY_CREATED + articleFolder);
            }
            Files.deleteIfExists(Paths.get(articleFolder + article.getTitle() + DOT + JPG_EXTENSION));  
            Files.copy(image.getInputStream(), articleFolder.resolve(article.getTitle() + DOT + JPG_EXTENSION), REPLACE_EXISTING);  
            article.setImageUrl(setArticleImageUrl(article.getTitle()));  
            articlesRepository.save(article);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM + image.getOriginalFilename());
        }
    }

    private String setArticleImageUrl(String article) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(ARTICLE_IMAGE_PATH + article + FORWARD_SLASH
        + article + DOT + JPG_EXTENSION).toUriString();
    }
}
