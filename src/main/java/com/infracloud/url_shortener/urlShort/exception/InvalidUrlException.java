package com.infracloud.url_shortener.urlShort.exception;

public class InvalidUrlException extends RuntimeException{

    public InvalidUrlException(String message){
        super(message);
    }
}
