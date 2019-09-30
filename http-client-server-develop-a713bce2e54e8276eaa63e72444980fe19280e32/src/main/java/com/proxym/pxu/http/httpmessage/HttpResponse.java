package com.proxym.pxu.http.httpmessage;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.javatuples.Pair ;

public class HttpResponse extends Message implements HttpResponseInterface{
    private int statusCode;
    private String reasonPhrase;

    public HttpResponse(int statusCode, String reasonPhrase, String httpVersion, List<Pair<String, String>> headers, byte[] body) throws InvalidHeaderException {
        super(httpVersion, headers, body);

        HttpStatusCodes.checkStatusCodeValid(statusCode);
        
        this.statusCode = statusCode;
        
        if (reasonPhrase == null || reasonPhrase.isEmpty()) {
            throw new InvalidHeaderException("Empty reason phrase");
        }
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public void writeToStream(OutputStream out) throws IOException {
        String line = "HTTP/" + this.getProtocolVersion() + " " + this.getStatusCode() + " " + getReasonPhrase();
        out.write(line.getBytes());
        out.write("\r\n".getBytes());

        super.writeToStream(out);
        out.flush();
    }
}