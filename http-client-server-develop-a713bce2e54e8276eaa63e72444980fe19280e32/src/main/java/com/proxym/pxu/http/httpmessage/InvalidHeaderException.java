
package com.proxym.pxu.http.httpmessage;

public class InvalidHeaderException extends Exception{
    private static final long serialVersionUID = 1L;
    
    public InvalidHeaderException(String msg) {
		super(msg);
	}
}