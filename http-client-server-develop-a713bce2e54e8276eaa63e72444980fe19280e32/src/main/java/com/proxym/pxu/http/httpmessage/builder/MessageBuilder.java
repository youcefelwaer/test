package com.proxym.pxu.http.httpmessage.builder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.List;

import com.proxym.pxu.http.httpmessage.InvalidHeaderException;
import com.proxym.pxu.http.httpmessage.InvalidMessageException;

public abstract class MessageBuilder implements MessageBuilderInterface {
    protected List<Pair<String, String>> headers = new ArrayList<Pair<String, String>>();
    protected byte[] body = {};
    protected String version = "1.1";

    @Override
    public MessageBuilder addHeader(String key, String val) {
        headers.add(new Pair<String, String>(key, val));
        return this;
    }

    @Override
    public MessageBuilder addHeader(String line) throws InvalidHeaderException {
        int idx = line.indexOf(':');
        if (idx < 0) {
            throw new InvalidHeaderException("Invalid header : " + line);
        }

        this.addHeader(line.substring(0, idx).trim(), line.substring(idx + 1).trim());
        return this;
    }

    @Override
    public MessageBuilder setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public MessageBuilder setBody(byte[] body) {
        this.body = body;
        return this;
    }

    @Override
    public MessageBuilder processStream(InputStream in) throws IOException, InvalidHeaderException, InvalidMessageException {
        BufferedReader br =new BufferedReader(new InputStreamReader(in));
        String firstLine = br.readLine();
        this.processFirstStreamLine(firstLine);
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if (line.isEmpty()){
                break;
            }
            
            this.addHeader(line);
        }

        int length = 0;
        for (Pair<String, String> header : headers) {
            if (header.getValue0().equalsIgnoreCase("Content-length")){
                length = Integer.valueOf(header.getValue1());
            }
        }
        
        char[] buffer = new char[length];
        if (br.read(buffer, 0, length) != length){
            throw new InvalidMessageException("Unable to read body");
        }
        // br.close();
        this.setBody(new String(buffer).getBytes());
        
        return this;
    }
}
