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
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UrlShortenerController {

    Logger log = LoggerFactory.getLogger(UrlShortenerController.class);
    private final UrlShortenerService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Object> createShortUrl(@RequestBody String url, HttpServletRequest request) {

        // Validation checks for supplied URL
        var validator = new UrlValidator(
                new String[]{"http", "https"}
        );
        if (!validator.isValid(url)) {
            log.error("Malformed Url provided");
            var error = new InvalidUrlException("Invalid URL: " + url);

            // returns a custom body with error message and bad request status code
            return ResponseEntity.badRequest().body(error);
        }
        String baseUrl;

        try {
            log.debug("Request:: " + request.getRequestURL().toString());
            baseUrl = UrlUtil.getBaseUrl(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            log.error("Malformed request url");
            throw new UrlShortenerException("Request url is invalid: " + e);
        }
        String shortUrl;
        var hash = urlService.checkUrlAlreadyExistsInMemory(url);
        // Retrieving the hash and concat with protocol://domain:port
        if(Objects.nonNull(hash))
            shortUrl = baseUrl.concat(hash);
        else
            shortUrl = baseUrl.concat(urlService.createShortUrl(url));

        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }
}
