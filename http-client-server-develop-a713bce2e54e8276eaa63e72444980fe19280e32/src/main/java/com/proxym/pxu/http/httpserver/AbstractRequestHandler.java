package com.proxym.pxu.http.httpserver;

import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;

public abstract class AbstractRequestHandler {
    abstract public HttpResponseInterface runHandler(HttpRequestInterface request) throws InvalidHeaderException;

    public Map<String, String> splitQuery(URL url) {
        try {
            Map<String, String> query_pairs = new LinkedHashMap<String, String>();
            String query = url.getQuery();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                        URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));

            }
            return query_pairs;
        } catch (Exception e) {
            return new LinkedHashMap<String, String>();
        }
    }

}