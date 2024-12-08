package com.infracloud.url_shortener.urlShort.storage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@RequiredArgsConstructor
@Component
public class UrlStorage {
    private final Map<String, String> fullUrlToShort;
    private final Map<String, String> shortToFullUrl;

    public void storeUrl(String originalUrl, String shortUrl) {
        fullUrlToShort.put(originalUrl, shortUrl);
        shortToFullUrl.put(shortUrl, originalUrl);
    }

    public String getShortenedUrl(String originalUrl) {
        return fullUrlToShort.get(originalUrl);
    }

    public String getOriginalUrl(String shortened) {
        return shortToFullUrl.get(shortened);
    }
}
