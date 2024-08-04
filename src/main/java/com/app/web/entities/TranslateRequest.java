package com.app.web.entities;

import lombok.Data;

@Data
public class TranslateRequest {

    private String sourceLanguage;

    private String targetLanguage;

    private String sentence;

    private String ipAddress;
}
