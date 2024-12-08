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
    private final Map<String, Integer> mostVisitDomains;

    public void storeUrl(String originalUrl, String shortUrl) {
        fullUrlToShort.put(originalUrl, shortUrl);
        shortToFullUrl.put(shortUrl, originalUrl);
        System.out.println("fullUrlToShort: " + fullUrlToShort);
        System.out.println("shortToFullUrl: " + shortToFullUrl);


        String domain = extractDomain(originalUrl);
        mostVisitDomains.put(domain, mostVisitDomains.getOrDefault(domain, 0) + 1);
    }

    public String getShortenedUrl(String originalUrl) {
        return fullUrlToShort.get(originalUrl);
    }

    public String getOriginalUrl(String shortened) {
        return shortToFullUrl.get(shortened);
    }

    public Map<String, Integer> getTopDomains() {
        System.out.println("mostVisitDomains: " + mostVisitDomains);
        return mostVisitDomains;
    }

    private String extractDomain(String url) {
        return url.split("/")[2]; // Simplistic domain extraction for demonstration
    }
}
