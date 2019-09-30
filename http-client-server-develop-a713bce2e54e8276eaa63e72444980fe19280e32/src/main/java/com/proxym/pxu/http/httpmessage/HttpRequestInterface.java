package com.proxym.pxu.http.httpmessage;

import java.net.URL;

public interface HttpRequestInterface extends MessageInterface{
    public String getMethod();
    public URL getUrl();
    public int getPort();
}