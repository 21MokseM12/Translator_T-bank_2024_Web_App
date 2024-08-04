package com.app.database.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class DataEncoder {

    public String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    public String decode(String data) {
        return new String(Base64.getDecoder().decode(data));
    }
}
