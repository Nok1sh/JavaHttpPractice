package org.example.randomFact.http;

public class FactHTTPException extends Exception {
    public FactHTTPException(String message) {
        super(message);
    }

    public FactHTTPException(String message, Throwable cause){
        super(message, cause);
    }
}
