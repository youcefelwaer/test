package com.proxym.pxu.cli;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.proxym.pxu.http.httpclient.HttpClient;
import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;
import com.proxym.pxu.http.httpmessage.InvalidMessageException;
import com.proxym.pxu.http.httpmessage.builder.HttpRequestBuilder;

import org.javatuples.Pair;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;;

/**
 * CommandLine compatible HttpClientCLI class
 *
 */
@Command(name = "HttpClientCLI", header = "%n@|Homemade curl like|@")
public class HttpClientCLI implements Runnable {

    @Option(names = { "--method", "-X" }, description = "HTTP method")
    String method = "GET";

    @Option(names = "-H", description = "Add Header")
    String[] headers = {};

    @Option(names = "--url", required = true, description = "URL")
    String url;

    @Option(names = "--body", description = "Body")
    String body = "";

    public void run() {
        HttpRequestBuilder reqBuilder = new HttpRequestBuilder();
        HttpRequestInterface req = null;

        try {
            reqBuilder.setMethod(method);
            reqBuilder.setURL(url);
            reqBuilder.setVersion("1.1");
            for (String header : headers) {
                reqBuilder.addHeader(header);
            }
            reqBuilder.setBody(body.getBytes());

            req = reqBuilder.build();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (InvalidHeaderException e) {
            e.printStackTrace();
        }

        HttpClient client = new HttpClient();

        try {
            HttpResponseInterface resp = client.sendRequest(req);
            for (Pair<String, String> header : resp.getHeaders()) {
                System.out.println(header.getValue0() + " " + header.getValue1());
            }
            System.out.println(new String(resp.getBody()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidHeaderException e) {
            e.printStackTrace();
        } catch (InvalidMessageException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        Logger.getGlobal().setLevel(Level.WARNING);
        new CommandLine(new HttpClientCLI())
            .execute(args);
    }
}
