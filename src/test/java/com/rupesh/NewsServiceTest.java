package com.rupesh;

import com.rupesh.service.NewsService;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest(startApplication = false)
class NewsServiceTest {

    @Inject
    NewsService newsService;

    @Timeout(4)
    @Test
    @Order(1)
    void firstInvocationOfNovemberDoesNotHitCache() {
        List<String> headlines = newsService.headlines(Month.NOVEMBER);
        assertEquals(2, headlines.size());
    }

    @Timeout(1)
    @Test
    @Order(2)
    void secondInvocationOfNovemberHitsCache() {
        List<String> headlines = newsService.headlines(Month.NOVEMBER);
        assertEquals(2, headlines.size());
    }

    @Timeout(4)
    @Test
    @Order(3)
    void firstInvocationOfOctoberDoesNotHitCache() {
        List<String> headlines = newsService.headlines(Month.OCTOBER);
        assertEquals(1, headlines.size());
    }

    @Timeout(1)
    @Test
    @Order(4)
    void secondInvocationOfOctoberHitsCache() {
        List<String> headlines = newsService.headlines(Month.OCTOBER);
        assertEquals(1, headlines.size());
    }

    @Timeout(1)
    @Test
    @Order(5)
    void addingAHeadlineToNovemberUpdatesCache() {
        List<String> headlines = newsService.addHeadline(Month.NOVEMBER, "Micronaut 1.3 Milestone 1 Released");
        assertEquals(1, headlines.size());
    }

    @Timeout(1)
    @Test
    @Order(6)
    void novemberCacheWasUpdatedByCachePutAndThusTheValueIsRetrievedFromTheCache() {
        List<String> headlines = newsService.headlines(Month.NOVEMBER);
        assertEquals(3, headlines.size());
    }

    @Timeout(1)
    @Test
    @Order(7)
    void invalidateNovemberCacheWithCacheInvalidate() {
        assertDoesNotThrow(() -> {
            newsService.removeHeadline(Month.NOVEMBER, "Micronaut 1.3 Milestone 1 Released");
        });
    }

    @Timeout(1)
    @Test
    @Order(8)
    void octoberCacheIsStillValid() {
        List<String> headlines = newsService.headlines(Month.OCTOBER);
        assertEquals(1, headlines.size());
    }

    @Timeout(4)
    @Test
    @Order(9)
    void novemberCacheWasInvalidated() {
        List<String> headlines = newsService.headlines(Month.NOVEMBER);
        assertEquals(2, headlines.size());
    }

}
