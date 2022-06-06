package com.example.consensus.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "news")
@Data
public class News {
    @Id
    @GeneratedValue(generator = "user_id")
    @GenericGenerator(
            name = "user_id",
            strategy = "com.example.consensus.generators.NewsIdGenerator"
    )
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "date_public")
    private Timestamp datePublic;

    @Column(name = "content")
    private String content;

    @Column(name = "url")
    private String url;

    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    @JsonIgnore
    private Company companyForNews;

    public void setFields(News news) {
        setAuthor(news.getAuthor());
        setContent(news.getContent());
        setUrl(news.getUrl());
        setDatePublic(news.getDatePublic());
        setTitle(news.getTitle());
    }
}
