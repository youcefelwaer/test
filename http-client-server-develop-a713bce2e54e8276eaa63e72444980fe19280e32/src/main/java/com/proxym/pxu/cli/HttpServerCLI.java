package com.proxym.pxu.cli;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;
import com.proxym.pxu.http.httpmessage.builder.HttpResponseBuilder;
import com.proxym.pxu.http.httpserver.AbstractRequestHandler;
import com.proxym.pxu.http.httpserver.HttpServer;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;;

/**
 * CommandLine compatible HttpServerCLI class
 *
 */
@Command(name = "HttpServerCLI", header = "%n@|Homemade HTTP server|@")
public class HttpServerCLI implements Runnable {

    @Option(names = { "--port", "-p" }, description = "Server port")
    int port = 80;

    public void run() {
        HttpServer server = new HttpServer(port);

        server.addHandler("/about", new AbstractRequestHandler() {

            @Override
            public HttpResponseInterface runHandler(HttpRequestInterface req) throws InvalidHeaderException {
                if (!req.getMethod().equals("GET")) {
                    return new HttpResponseBuilder()
                        .setStatusCode(405)
                        .build();
                }

                String html = "<html><head><title>Proxym-U HTTP Server v0.1.0</title></head><body><h1>Proxym-U HTTP Server v0.1.0</h1></body></html>";
                return new HttpResponseBuilder()
                    .setStatusCode(200)
                    .setBody(html.getBytes())
                    .build();
            }
        });

        server.addHandler("/hello", new AbstractRequestHandler() {

            @Override
            public HttpResponseInterface runHandler(HttpRequestInterface req) throws InvalidHeaderException {
                if (!req.getMethod().equals("GET")) {
                    return new HttpResponseBuilder()
                        .setStatusCode(405)
                        .build();
                }

                String username = "";
                Map<String, String> queries = this.splitQuery(req.getUrl());
                String html = "";
                
                if (queries.containsKey("user")){
                    html = "<html><head><title>hello "
                        + queries.get("user")
                        + "</title></head><body><h1>Hello " 
                        + queries.get("user")
                        + "</h1></body></html>";
                }else{
                    html = "<html><head><title>No username provided</title></head><body><h1>No username provided</h1></body></html>";
                }
                
                return new HttpResponseBuilder()
                    .setStatusCode(200)
                    .setBody(html.getBytes())
                    .build();
            }
        });

        try {
            server.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        Logger.getGlobal().setLevel(Level.WARNING);
        new CommandLine(new HttpServerCLI())
            .execute(args);
    }
}
