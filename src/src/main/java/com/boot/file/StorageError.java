package com.boot.file;


import com.boot.exceptions.ServiceError;

public class StorageError extends ServiceError {
	
	public StorageError(String message) {
		super(message);
	}
	
	public StorageError(String message, Throwable t) {
		super(message, t);
	}

}
