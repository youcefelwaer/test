package com.proxym.pxu.http.httpmessage;

import org.javatuples.Pair ;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Message implements MessageInterface {
    private List<Pair<String, String>> headers;
    private byte[] body = {};
    private String version;

    public Message(String version, List<Pair<String, String>> headers, byte[] body) throws InvalidHeaderException {
        if (version.isEmpty()){
            throw new InvalidHeaderException("Invalid version : " + version);
        }
        this.version = version;

        if (headers == null){
            this.headers = new ArrayList<Pair<String, String>>();
        }else{
            this.headers = new ArrayList<Pair<String, String>>(headers);
        }

        if (body == null){
            this.body = new byte[0];
        }else{
            this.body = body;
        }
    }

    @Override
    public List<Pair<String, String>> getHeaders() {
        return headers;
    }

    @Override
    public boolean hasHeader(String name) {
        for (Pair<String, String> header : headers) {
            if (header.getValue0().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getProtocolVersion() {
        return version;
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public void writeToStream(OutputStream out) throws IOException {
        for (Pair<String, String> header : headers) {
            String line = header.getValue0()+": "+header.getValue1();
            out.write(line.getBytes());
            out.write("\r\n".getBytes());
        }
        out.write("\r\n".getBytes());
        if (this.body != null){
            out.write(this.getBody());
        }
	}
}