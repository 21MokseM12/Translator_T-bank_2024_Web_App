package com.app.translator.entities;

import com.app.translator.exceptions.RestTemplateHandlerException;
import com.app.translator.http.RestTemplateHandler;
import com.app.translator.parsers.JsonParserAPI;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Callable;

@Slf4j
@Setter
public class Task implements Callable<String> {

    private String subSentence;

    private String sourceLanguage;

    private String targetLanguage;

    private final RestTemplateHandler restTemplateHandler;

    private final JsonParserAPI parser;

    public Task(String subSentence, String sourceLanguage, String targetLanguage, RestTemplateHandler restTemplateHandler, JsonParserAPI parser) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.subSentence = subSentence;
        this.restTemplateHandler = restTemplateHandler;
        this.parser = parser;
    }

    @Override
    public String call() {
        return translateSubSentence(sourceLanguage, targetLanguage, subSentence);
    }

    public String translateSubSentence(String sourceLanguage, String targetLanguage, String subSentence) {
        try {
            ResponseEntity<String> response = restTemplateHandler.POST(sourceLanguage, targetLanguage, subSentence);
            return parser.parse(response.getBody());
        } catch (RestTemplateHandlerException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
