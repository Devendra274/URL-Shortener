package com.infracloud.url_shortener.urlShort;

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

    @ParameterizedTest
    @MethodSource
    void test_createShortUrl(String originalUrl) {
        //Given
        int port = 8090;
        String baseUrl="http://localhost";

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
    }}