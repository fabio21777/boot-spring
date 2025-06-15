package com.boot.exceptions;

public class NotFoundService extends ServiceError {

    public NotFoundService(String message) {
        super(message);
    }

    public NotFoundService(String message, Throwable t) {
        super(message, t);
    }
}
