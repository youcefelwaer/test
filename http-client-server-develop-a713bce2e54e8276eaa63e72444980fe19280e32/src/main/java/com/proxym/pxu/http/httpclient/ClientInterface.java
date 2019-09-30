package com.proxym.pxu.http.httpclient;

import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;
import com.proxym.pxu.http.httpmessage.InvalidMessageException;

import java.io.IOException;
import java.net.UnknownHostException;



interface ClientInterface{
    public HttpResponseInterface sendRequest(HttpRequestInterface request) 
        throws UnknownHostException, IOException, InvalidHeaderException, InvalidMessageException;
}
