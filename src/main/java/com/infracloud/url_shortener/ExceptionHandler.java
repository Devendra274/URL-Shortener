package com.infracloud.url_shortener;

import com.infracloud.url_shortener.urlShort.exception.InvalidUrlException;
import com.infracloud.url_shortener.urlShort.exception.UrlShortenerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.print.DocFlavor;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = InvalidUrlException.class)
    public ResponseEntity<String> handleException(InvalidUrlException ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UrlShortenerException.class)
    public ResponseEntity<String> handleException(UrlShortenerException ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
