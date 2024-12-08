package com.infracloud.url_shortener.urlShort;

import com.infracloud.url_shortener.urlShort.storage.UrlStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private static final String ALPHANUMERIC = "bwYAJiKUlLcTCjZ8Io2puX0VxQM43shEnD7v5FkSmyz9g16RWdNBeHarPqOtGf";
    private static final int BASE = ALPHANUMERIC.length();
    private static final int SHORT_URL_LENGTH = 7;
    private final UrlStorage urlStorage;

    public String createShortUrl(String originalUrl, String baseUrl) {
        var hash = checkUrlAlreadyExistsInMemory(originalUrl);
        // Retrieving the hash and concat with protocol://domain:port
        if(Objects.nonNull(hash)) {
            return baseUrl.concat(hash);
        }
        else {
            createShortUrlInMemory(originalUrl);
            return baseUrl.concat(urlStorage.getShortenedUrl(originalUrl));
        }
    }

    public void createShortUrlInMemory(String originalUrl) {
        var hash = randomAlphaNumericWord();
        urlStorage.storeUrl(originalUrl, hash);
    }

    public String checkUrlAlreadyExistsInMemory(String originalUrl) {
        return urlStorage.getShortenedUrl(originalUrl);
    }

    public String redirectURL(String shortened) {
        return urlStorage.getOriginalUrl(shortened);
    }

    public Map<String, Integer> getTopDomains() {
         return urlStorage.getTopDomains().entrySet().stream()
                 .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                 .limit(3)
                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String randomAlphaNumericWord() {
        var shorten = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = (int) (Math.random() * BASE);
            shorten.append(ALPHANUMERIC.charAt(index));
        }

        if(Objects.nonNull(urlStorage.getOriginalUrl(shorten.toString())))
            randomAlphaNumericWord();

        return shorten.toString();
    }
}
