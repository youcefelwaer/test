package com.proxym.pxu.http.httpmessage;

import org.javatuples.Pair;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface MessageInterface {
    public String getProtocolVersion();
    public List<Pair<String, String>> getHeaders();
    public boolean hasHeader(String name);
    public byte[] getBody();
    public void writeToStream(OutputStream out) throws IOException;
}
