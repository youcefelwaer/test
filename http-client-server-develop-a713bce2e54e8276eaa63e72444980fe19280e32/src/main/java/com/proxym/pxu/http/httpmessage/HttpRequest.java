package com.proxym.pxu.http.httpmessage;

import java.util.List;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import org.javatuples.Pair ;

public class HttpRequest extends Message implements HttpRequestInterface{
    private String method;
    private URL url;

    public HttpRequest(URL url, String method, String httpVersion, List<Pair<String, String>> headers, byte[] body)
            throws InvalidHeaderException {
        super(httpVersion, headers, body);

        this.url = url;

        if (method == null || method.isEmpty()) {
            throw new InvalidHeaderException("Empty method");
        }
        
        this.method = method;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public int getPort(){
        if (this.url.getPort() == -1){
            return 80;
        }
        return this.url.getPort();
    }

    @Override
    public void writeToStream(OutputStream out) throws IOException {
        String line = this.getMethod()+ " " + this.getUrl().getFile() + " HTTP/" + this.getProtocolVersion();
        out.write(line.getBytes());
        out.write("\r\n".getBytes());

        line = "Host: " + this.getUrl().getHost();
        if (this.getPort() != 80){
            line += ":" + String.valueOf(this.getPort());
        }
        out.write(line.getBytes());
        out.write("\r\n".getBytes());

        super.writeToStream(out);
        out.flush();
    }
}