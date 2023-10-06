package com.rupesh.service;

import io.micronaut.cache.annotation.CacheConfig;
import io.micronaut.cache.annotation.CacheInvalidate;
import io.micronaut.cache.annotation.CachePut;
import io.micronaut.cache.annotation.Cacheable;
import jakarta.inject.Singleton;

import java.time.Month;
import java.util.*;

@Singleton
@CacheConfig("headlines")
public class NewsService {

    Map<Month, List<String>> headlines = new HashMap<>();

    @Cacheable
    public List<String> headlines(Month month) {
        return headlines.get(month);
    }

    @CachePut(parameters = {"month"})
    public List<String> addHeadline(Month month, String headline) {
        if (headlines.containsKey(month)) {
            List<String> l = new ArrayList<>(headlines.get(month));
            l.add(headline);
            headlines.put(month, l);
        } else {
            headlines.put(month, Collections.singletonList(headline));
        }
        return headlines.get(month);
    }

    @CacheInvalidate(parameters = {"month"})
    public void removeHeadline(Month month, String headline) {
        if (headlines.containsKey(month)) {
            List<String> l = new ArrayList<>(headlines.get(month));
            l.remove(headline);
            headlines.put(month, l);
        }
    }
}