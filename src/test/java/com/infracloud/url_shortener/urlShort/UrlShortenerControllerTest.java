package com.infracloud.url_shortener.urlShort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerControllerTest {

    @InjectMocks
    UrlShortenerController urlShortenerController;

    @Mock
    UrlShortenerService urlShortenerService;

    @Test
    void test_createShortUrl() {
        //Given
        var local = "http://localhost/";
        var originalUrl = "http://example.com";
        var urlBody = new HashMap<String, String>();
        urlBody.put("url", originalUrl);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName(local);
        request.setLocalPort(8090);

        //When
        when(urlShortenerService.createShortUrl(originalUrl, local)).thenReturn(local+"a74XeT2");
        var response = urlShortenerController.createShortUrl(urlBody, request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void test_redirect() {
        //Given
        var local = "http://localhost/";
        var originalUrl = "http://example.com";
        var urlBody = new HashMap<String, String>();
        urlBody.put("url", originalUrl);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName(local);
        request.setLocalPort(8090);

        //When
        urlShortenerController.createShortUrl(urlBody, request);
        var response = urlShortenerController.redirect("a74XeT2");

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }
}