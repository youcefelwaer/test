package com.proxym.pxu.http.httpmessage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final public class HttpStatusCodes {
    private static final Map<Integer, String> codePhraseMap;
    static {
        Map<Integer, String> tempMap = new HashMap<Integer, String>();
        tempMap.put(100, "Continue");
        tempMap.put(101, "Switching Protocols");
        tempMap.put(102, "Processing");
        tempMap.put(103, "Early Hints");
        tempMap.put(200, "OK");
        tempMap.put(201, "Created");
        tempMap.put(202, "Accepted");
        tempMap.put(203, "Non-Authoritative Information");
        tempMap.put(204, "No Content");
        tempMap.put(205, "Reset Content");
        tempMap.put(206, "Partial Content");
        tempMap.put(207, "Multi-Status");
        tempMap.put(208, "Already Reported");
        tempMap.put(226, "IM Used");
        tempMap.put(300, "Multiple Choices");
        tempMap.put(301, "Moved Permanently");
        tempMap.put(302, "Found");
        tempMap.put(303, "See Other");
        tempMap.put(304, "Not Modified");
        tempMap.put(305, "Use Proxy");
        tempMap.put(306, "(Unused)");
        tempMap.put(307, "Temporary Redirect");
        tempMap.put(308, "Permanent Redirect");
        tempMap.put(400, "Bad Request");
        tempMap.put(401, "Unauthorized");
        tempMap.put(402, "Payment Required");
        tempMap.put(403, "Forbidden");
        tempMap.put(404, "Not Found");
        tempMap.put(405, "Method Not Allowed");
        tempMap.put(406, "Not Acceptable");
        tempMap.put(407, "Proxy Authentication Required");
        tempMap.put(408, "Request Timeout");
        tempMap.put(409, "Conflict");
        tempMap.put(410, "Gone");
        tempMap.put(411, "Length Required");
        tempMap.put(412, "Precondition Failed");
        tempMap.put(413, "Payload Too Large");
        tempMap.put(414, "URI Too Long");
        tempMap.put(415, "Unsupported Media Type");
        tempMap.put(416, "Range Not Satisfiable");
        tempMap.put(417, "Expectation Failed");
        tempMap.put(418, "I'm a teapot");
        tempMap.put(421, "Misdirected Request");
        tempMap.put(422, "Unprocessable Entity");
        tempMap.put(423, "Locked");
        tempMap.put(424, "Failed Dependency");
        tempMap.put(425, "Too Early");
        tempMap.put(426, "Upgrade Required");
        tempMap.put(428, "Precondition Required");
        tempMap.put(429, "Too Many Requests");
        tempMap.put(431, "Request Header Fields Too Large");
        tempMap.put(451, "Unavailable For Legal Reasons");
        tempMap.put(500, "Internal Server Error");
        tempMap.put(501, "Not Implemented");
        tempMap.put(502, "Bad Gateway");
        tempMap.put(503, "Service Unavailable");
        tempMap.put(504, "Gateway Timeout");
        tempMap.put(505, "HTTP Version Not Supported");
        tempMap.put(506, "Variant Also Negotiates");
        tempMap.put(507, "Insufficient Storage");
        tempMap.put(508, "Loop Detected");
        tempMap.put(510, "Not Extended");
        tempMap.put(511, "Network Authentication Required");

        codePhraseMap = Collections.unmodifiableMap(tempMap);
    }

    public static void checkStatusCodeValid(int statusCode) throws InvalidHeaderException {
        if (statusCode >= 600 || statusCode < 100){
            throw new InvalidHeaderException("Invalid status code : " + statusCode);
        }
    }

    public static String getPhrase(int statusCode) throws InvalidHeaderException {
        if (codePhraseMap.containsKey(statusCode)){
            return codePhraseMap.get(statusCode);
        }
        else{
            checkStatusCodeValid(statusCode);
            return String.valueOf(statusCode);
        }
    }
}