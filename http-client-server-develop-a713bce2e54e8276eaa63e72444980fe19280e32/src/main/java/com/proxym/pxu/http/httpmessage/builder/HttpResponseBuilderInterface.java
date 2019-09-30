package com.proxym.pxu.http.httpmessage.builder;

import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;

public interface HttpResponseBuilderInterface extends MessageBuilderInterface{
    public HttpResponseBuilderInterface setReasonPhrase(String reasonPhrase);
    public HttpResponseBuilderInterface setStatusCode(int statusCode) throws InvalidHeaderException;
    public HttpResponseInterface build() throws InvalidHeaderException;
}
