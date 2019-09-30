package com.proxym.pxu.http.httpmessage.builder;

import java.io.IOException;
import java.io.InputStream;

import com.proxym.pxu.http.httpmessage.InvalidHeaderException;
import com.proxym.pxu.http.httpmessage.InvalidMessageException;

public interface MessageBuilderInterface{
    public MessageBuilderInterface addHeader(String key, String val);
    public MessageBuilderInterface addHeader(String line) throws InvalidHeaderException;
    public MessageBuilderInterface setVersion(String version);
    public MessageBuilderInterface setBody(byte[] body);
    public MessageBuilderInterface processStream(InputStream in) throws IOException, InvalidHeaderException, InvalidMessageException;
    public MessageBuilderInterface processFirstStreamLine(String line) throws InvalidHeaderException;
}
