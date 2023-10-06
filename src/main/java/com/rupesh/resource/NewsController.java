package com.rupesh.resource;

import com.rupesh.mdel.News;
import com.rupesh.service.NewsService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.time.Month;

@Controller("/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Get("/{month}")
    public News index(Month month) {
        return new News(month, newsService.headlines(month));
    }

}