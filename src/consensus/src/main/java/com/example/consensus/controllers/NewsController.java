package com.example.consensus.controllers;

import com.example.consensus.entities.Company;
import com.example.consensus.entities.Forecast;
import com.example.consensus.entities.News;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.services.CompanyService;
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
    private final CompanyService companyService;

    @GetMapping("/company/{id}/news")
    public ResponseEntity<?> getAllNewsByCompanyId(@PathVariable(name="id") Long id) {
        List<News> newsList = newsService.getAllNewsByCompanyId(id);

        if (newsList.size() == 0) {
            return new ResponseEntity<>("Отсутствуют новости на данную компанию", HttpStatus.OK);
        }
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

    @PostMapping("/company/{id}/news")
    public ResponseEntity<?> addNews(@PathVariable(name = "id") Long id, @RequestBody News newsNew) {
        Company company;
        try {
            company = companyService.getCompanyById(id);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Компания с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        newsNew.setCompanyForNews(company);
        News news;
        news = newsService.addNews(newsNew);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable(name = "id") Long id) {
        try {
            newsService.deleteNews(id);
        }  catch (Exception exc) {
            return new ResponseEntity<>("Новость с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Новость с id = " + id + " успешно удалена!", HttpStatus.OK);
    }
}
