package com.infracloud.url_shortener.urlShort;

import com.infracloud.url_shortener.urlShort.exception.InvalidUrlException;
import com.infracloud.url_shortener.urlShort.exception.UrlShortenerException;
import com.infracloud.url_shortener.urlShort.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UrlShortenerController {

    Logger log = LoggerFactory.getLogger(UrlShortenerController.class);
    private final UrlShortenerService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Object> createShortUrl(@RequestBody String url, HttpServletRequest request) {
        validateUrl(url);
        String baseUrl = getBaseUrl(request);
        String shortUrl;
        shortUrl = urlService.createShortUrl(url, baseUrl);
        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }

    @GetMapping("/redirect/{shortened}")
    public ResponseEntity<Void> redirect(@PathVariable String shortened) {
        String originalUrl = urlService.redirectURL(shortened);
        return ResponseEntity.status(302).header("Location", originalUrl).build();
    }

    private void validateUrl(String url) {
        // Validation checks for supplied URL
        var validator = new UrlValidator(
                new String[]{"http", "https"}
        );
        if (!validator.isValid(url)) {
            log.error("Malformed Url provided");
            throw new InvalidUrlException("Invalid URL: " + url);
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        String baseUrl;
        try {
            log.debug("Request:: " + request.getRequestURL().toString());
            baseUrl = UrlUtil.getBaseUrl(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            log.error("Malformed request url");
            throw new UrlShortenerException("Request url is invalid: " + e);
        }
        return baseUrl;
    }
}
