package com.proxym.pxu.http.httpmessage.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.proxym.pxu.http.httpmessage.HttpResponse;
import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.HttpStatusCodes;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;

public class HttpResponseBuilder extends MessageBuilder implements HttpResponseBuilderInterface {
    private int statusCode;
    private String reasonPhrase;

    public HttpResponseBuilder setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
        return this;
    }

    public HttpResponseBuilder setStatusCode(int statusCode) throws InvalidHeaderException {
        this.statusCode = statusCode;

        if (reasonPhrase == null || reasonPhrase.isEmpty()) {
            try {
                this.reasonPhrase = HttpStatusCodes.getPhrase(statusCode);
            } catch (InvalidHeaderException e) {
                throw new InvalidHeaderException(e.getMessage());
            }
        }
        return this;
    }

    @Override
    public HttpResponseBuilder setBody(byte[] body) {
        return (HttpResponseBuilder) super.setBody(body);
    }

    @Override
    public HttpResponseBuilder setVersion(String version) {
        return (HttpResponseBuilder) super.setVersion(version);
    }

    @Override
    public HttpResponseBuilder addHeader(String key, String val) {
        return (HttpResponseBuilder) super.addHeader(key, val);
    }

    @Override
    public HttpResponseBuilder addHeader(String line) throws InvalidHeaderException {
        return (HttpResponseBuilder) super.addHeader(line);
    }

    public HttpResponseInterface build() throws InvalidHeaderException {
        return new HttpResponse(this.statusCode, this.reasonPhrase, 
                                this.version, this.headers, this.body);
    }

    @Override
    public HttpResponseBuilder processFirstStreamLine(String line) throws InvalidHeaderException{
        if (line.isEmpty()){
            throw new InvalidHeaderException("Unable to parse line");
        }
        Pattern r = Pattern.compile("HTTP(\\S+) (\\S+) (\\S+)");
        Matcher m = r.matcher(line);
        
        if (! m.find()){
            throw new InvalidHeaderException("Unable to parse line");
        }

        String version = m.group(1);
        int statusCode =  Integer.parseInt(m.group(2));
        String reasonPhrase = m.group(3);

        this.setVersion(version);
        this.setStatusCode(statusCode);
        this.setReasonPhrase(reasonPhrase);

        return this;
    }
}
