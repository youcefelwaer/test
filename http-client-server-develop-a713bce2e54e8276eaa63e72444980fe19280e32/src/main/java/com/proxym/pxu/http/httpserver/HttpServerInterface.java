package com.proxym.pxu.http.httpserver;

import java.io.IOException;

public interface HttpServerInterface {

    public void addHandler(String path, AbstractRequestHandler handler);
    public void start() throws IOException;
}