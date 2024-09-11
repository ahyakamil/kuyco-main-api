package com.kuyco.main_api.exception;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException() {
        super();
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(Throwable cause) {
        super(cause);
    }
}