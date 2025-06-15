package com.boot.file;

import com.boot.security.user.User;

import java.io.InputStream;

public interface StorageService {
	
	File store(String originalName, String bucket, User user, InputStream content);
	
	InputStream read(File file, User user);
	
	void remove(File arquivo);
	
	String getType();

}
