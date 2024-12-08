package com.infracloud.url_shortener.urlShort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UrlShortenerControllerTest {

    @Autowired
    UrlShortenerController urlShortenerController;

    @Test
    void createShortUrl() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("http://localhost");
        request.setLocalPort(8090);

        var response = urlShortenerController.createShortUrl("http://example.com", request);

        assertNotNull(response);
    }
}