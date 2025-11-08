package org.example.randomCatFact.http;

public class CatFactHTTPException extends Exception {
    public CatFactHTTPException(String message) {
        super(message);
    }

    public CatFactHTTPException(String message, Throwable cause){
        super(message, cause);
    }
}
