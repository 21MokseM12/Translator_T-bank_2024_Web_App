package com.app.config;

import com.app.database.utils.DataEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
@Slf4j
public class PropertiesManager {

    private final Properties PROPERTIES = new Properties();

    private final DataEncoder encoder;

    @Autowired
    public PropertiesManager(DataEncoder encoder) {
        this.encoder = encoder;
        try (InputStream input = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(input);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public String get(String key) {
        if (key.equals("translator.api.token")) return encoder.decode(PROPERTIES.getProperty(key));
        else return PROPERTIES.getProperty(key);
    }
}
