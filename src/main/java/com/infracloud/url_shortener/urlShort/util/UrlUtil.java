package com.infracloud.url_shortener.urlShort.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {

    public static String getBaseUrl(String url) throws MalformedURLException {
        var reqUrl = new URL(url);
        var protocol = reqUrl.getProtocol();
        var host = reqUrl.getHost();
        int port = reqUrl.getPort();

        if (port == -1) {
            return String.format("%s://%s/", protocol, host);
        } else {
            return String.format("%s://%s:%d/", protocol, host, port);
        }
    }
}
