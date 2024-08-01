package com.app.web.entity;

import lombok.Data;

@Data
public class TranslateRequest {

    private String sourceLanguage;

    private String targetLanguage;

    private String sentence;
}
