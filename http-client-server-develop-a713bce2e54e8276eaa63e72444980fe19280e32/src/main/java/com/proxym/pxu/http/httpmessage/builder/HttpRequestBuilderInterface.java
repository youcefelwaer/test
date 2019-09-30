package com.proxym.pxu.http.httpmessage.builder;

import java.net.MalformedURLException;

import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;

public interface HttpRequestBuilderInterface extends MessageBuilderInterface{
    public HttpRequestBuilderInterface setMethod(String method);
    public HttpRequestBuilderInterface setURL(String url) throws MalformedURLException;
    public HttpRequestInterface build() throws MalformedURLException, InvalidHeaderException;
}
