package com.boot.exceptions;

public class ServiceError extends RuntimeException {
	
	public ServiceError(String message) {
		super(message);
	}
	
	public ServiceError(String message, Throwable t) {
		super(message, t);
	}

}
