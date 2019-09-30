package com.proxym.pxu.http.httpmessage;

public interface HttpResponseInterface extends MessageInterface{
    public int getStatusCode();
    public String getReasonPhrase();
}