package com.example.consensus.controllers;

import com.example.consensus.entities.News;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/company/{id}/news")
    public ResponseEntity<List<?>> getAllNewsByCompanyId(@PathVariable(name="id") Long id) {
        List<News> newsList = newsService.getAllNewsByCompanyId(id);
        return new ResponseEntity<>(newsList, HttpStatus.OK);
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsById(@PathVariable(name = "id") Long id) {
        News news;
        try {
            news = newsService.getNewsById(id);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Новость с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<?> updateNewsById(@PathVariable(name = "id") Long id, @RequestBody News newsDetails) {
        News news;
        try {
            news = newsService.updateNewsById(id, newsDetails);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Новость с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @PostMapping("/news/")
    public ResponseEntity<?> addNews(@RequestBody News newsNew) {
        News news;
            news = newsService.addNews(newsNew);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable(name = "id") Long id) {
        News news;
        try {
            news = newsService.deleteNews(id);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Новость с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(news, HttpStatus.OK);
    }
}
