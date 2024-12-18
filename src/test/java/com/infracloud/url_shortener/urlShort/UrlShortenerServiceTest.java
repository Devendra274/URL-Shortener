package com.infracloud.url_shortener.urlShort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlShortenerServiceTest {

    @Autowired
    UrlShortenerService urlShortenerService;

    private static final String BASE_URL = "http://localhost/";

    @ParameterizedTest
    @MethodSource
    void test_createShortUrl(String originalUrl) {
        //Given
        int port = 8080;
        String baseUrl="http://localhost/";

        //When
        String shortUrl = urlShortenerService.createShortUrl(originalUrl, baseUrl);

        //Then
        assertNotNull(shortUrl);
    }

    private static Stream<Arguments> test_createShortUrl() {
        return Stream.of(
                Arguments.of("https://example.com"),
                Arguments.of("https://example.com/order")
        );
    }

    @Test
    void testRedirectURL() {
        //Given
        String originalUrl = "https://example.com";

        //When
        String shortUrl = urlShortenerService.createShortUrl(originalUrl, BASE_URL);
        shortUrl = shortUrl.split("/")[3];
        System.out.println(shortUrl);

        String redirectedUrl = urlShortenerService.redirectURL(shortUrl);
        System.out.println(redirectedUrl);

        //Then
        assertEquals(originalUrl, redirectedUrl);
    }

    @Test
    void testMetrics() {
        //Given
        urlShortenerService.createShortUrl("https://youtube.com/account", BASE_URL);
        urlShortenerService.createShortUrl("https://youtube.com/admin", BASE_URL);
        urlShortenerService.createShortUrl("https://youtube.com/audio", BASE_URL);
        urlShortenerService.createShortUrl("https://udemy.com/user", BASE_URL);
        urlShortenerService.createShortUrl("https://udemy.com/trainer", BASE_URL);
        urlShortenerService.createShortUrl("https://udemy.com/course", BASE_URL);
        urlShortenerService.createShortUrl("https://irctc.com/trains", BASE_URL);
        urlShortenerService.createShortUrl("https://irctc.com/agent", BASE_URL);
        urlShortenerService.createShortUrl("https://asics.com/shoes", BASE_URL);

        //When
        var metrics = urlShortenerService.getTopDomains();

        //Then
        assertEquals(3, metrics.get("youtube.com"));
        assertEquals(3, metrics.get("udemy.com"));
        assertNull(metrics.get("asics.com"));
    }
}