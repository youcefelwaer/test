package com.proxym.pxu.http.httpserver;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;
import com.proxym.pxu.http.httpmessage.InvalidMessageException;
import com.proxym.pxu.http.httpmessage.builder.HttpRequestBuilder;
import com.proxym.pxu.http.httpmessage.builder.HttpResponseBuilder;

public class HttpServer implements HttpServerInterface{
    int port;
    HashMap<String, AbstractRequestHandler> handlersMap = new HashMap<>();

    public HttpServer(int port) {
        this.port = port;
    }
    @Override
    public void addHandler(String path, AbstractRequestHandler handler) {
        handlersMap.put(path, handler);
    }

    @Override
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        System.err.println("Serveur lancé sur le port : " + port);

        while (serverSocket.isBound()) {
            Socket clientSocket = serverSocket.accept();
            try {

                HttpRequestInterface req = null;
                HttpRequestBuilder builder = new HttpRequestBuilder();
                try {
                    builder.processStream(clientSocket.getInputStream());
                    req = builder.build();
                } catch (InvalidHeaderException e) {
                    System.err.println("REQUEST HEADER PROBLEM : " + e.getMessage());
                } catch (InvalidMessageException e) {
                    System.err.println("REQUEST MESSAGE PROBLEM : " + e.getMessage());
                }

                try {
                    HttpResponseInterface resp;
                    if (handlersMap.containsKey(req.getUrl().getPath())) {
                        resp = handlersMap.get(req.getUrl().getPath()).runHandler(req);
                    } else {
                        resp = new HttpResponseBuilder()
                            .setStatusCode(404)
                            .setBody("<html><body><h1>Not Found</h1></body></html>".getBytes())
                            .build();
                    }

                    resp.writeToStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                } catch (InvalidHeaderException e) {
                    System.err.println("RESPONSE HEADER PROBLEM : " + e.getMessage());
                }
            } finally {
                clientSocket.close();
            }
        }
        serverSocket.close();
    }
}