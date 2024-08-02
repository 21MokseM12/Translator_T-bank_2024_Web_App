package com.app.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
@Slf4j
public class PropertiesManager {

    private final Properties PROPERTIES = new Properties();

    public PropertiesManager() {
        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
