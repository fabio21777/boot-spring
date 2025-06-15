package com.boot;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UtilsTest {

    public  static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
