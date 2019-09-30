package com.proxym.pxu.http.httpclient;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.proxym.pxu.http.httpmessage.HttpRequestInterface;
import com.proxym.pxu.http.httpmessage.HttpResponseInterface;
import com.proxym.pxu.http.httpmessage.InvalidHeaderException;
import com.proxym.pxu.http.httpmessage.InvalidMessageException;
import com.proxym.pxu.http.httpmessage.builder.HttpResponseBuilder;

public class HttpClient implements ClientInterface {

    @Override
    public HttpResponseInterface sendRequest(HttpRequestInterface req)
            throws UnknownHostException, IOException, InvalidHeaderException, InvalidMessageException {
        
        Socket socket = new Socket(InetAddress.getByName(req.getUrl().getHost()), req.getPort());
        req.writeToStream(new BufferedOutputStream(socket.getOutputStream()));
        
        HttpResponseBuilder builder = new HttpResponseBuilder();
        builder.processStream(socket.getInputStream());
        socket.close();
        
        return builder.build();
    }
}
