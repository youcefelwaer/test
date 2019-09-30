package com.proxym.pxu.http.httpmessage.builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.proxym.pxu.http.httpmessage.HttpRequest;
import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;

import org.javatuples.Pair;

public class HttpRequestBuilder extends MessageBuilder implements HttpRequestBuilderInterface{
    private String method = "GET";
    private URL url = null;
    private String path = null;

    public HttpRequestBuilder setMethod(String method) {
        this.method = method.toUpperCase();
        return this;
    }

    public HttpRequestBuilder setURL(String url) throws MalformedURLException {
        this.url = new URL(url);
        return this;
    }

    @Override
    public HttpRequestBuilder setBody(byte[] body) {
        return (HttpRequestBuilder) super.setBody(body);
    }

    @Override
    public HttpRequestBuilder setVersion(String version) {
        return (HttpRequestBuilder) super.setVersion(version);
    }

    @Override
    public HttpRequestBuilder addHeader(String key, String val) {
        return (HttpRequestBuilder) super.addHeader(key, val);
    }

    @Override
    public HttpRequestBuilder addHeader(String line) throws InvalidHeaderException {
        return (HttpRequestBuilder) super.addHeader(line);
    }
    
    public HttpRequestInterface build() throws MalformedURLException, InvalidHeaderException {
        // generte url if needed
        if (this.url == null){
            String host = null;
            for (Pair<String, String> header : headers) {
                if (header.getValue0().equalsIgnoreCase("Host")){
                    host = header.getValue1();
                }
            }
            if (host.isEmpty() || path.isEmpty()){
                throw new MalformedURLException("Unable to regenrate URL");
            }
            
            this.setURL("http://"+host+this.path);
        }

        // content length
        if (this.body != null && this.body.length > 0){
            this.addHeader("Content-length", String.valueOf(this.body.length));
        }

        // connection close
        boolean isConnectionHeader = false;
        for (Pair<String, String> header : headers) {
            if (header.getValue0().equalsIgnoreCase("Connection")){
                isConnectionHeader = true;
            }
        }
        if (! isConnectionHeader){
            this.addHeader("Connection", "Close");
        }
        return new HttpRequest(this.url, this.method, this.version, this.headers, this.body);
    }

    @Override
    public HttpRequestBuilder processFirstStreamLine(String line) throws InvalidHeaderException {
        if (line.isEmpty()){
            throw new InvalidHeaderException("Unable to parse line");
        }
        Pattern r = Pattern.compile("(\\S+) (\\S+) HTTP/(\\S+)");
        Matcher m = r.matcher(line);
        
        if (! m.find()){
            throw new InvalidHeaderException("Unable to parse line");
        }

        String method = m.group(1);
        String path =  m.group(2);
        String version = m.group(3);

        this.setVersion(version);
        this.setMethod(method);
        this.path = path;

        return this;
    }
}
