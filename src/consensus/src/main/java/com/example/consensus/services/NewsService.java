package com.example.consensus.services;


import com.example.consensus.entities.News;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public List<News> getAllNewsByCompanyId(Long id) {
        return newsRepository.findByCompanyForNewsId(id);
    }

    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElseThrow(() -> new FileStorageException("News with id = " + id + " not exists!"));
    }

    public News updateNewsById(Long id, News news) {
        News newsNew = newsRepository.findById(id).orElseThrow(() -> new FileStorageException("News with id = " + id + " not exists!"));
        newsNew.setFields(news);
        return newsRepository.save(newsNew);
    }

    public News addNews(News news) {
        return newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
