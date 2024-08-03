package com.app.translator.http;

import com.app.translator.enums.HttpKeysConstants;
import com.app.translator.exceptions.RestTemplateHandlerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestTemplateHandler {

    @Value("${translator.api.token}")
    private String token;

    private static final String source = "https://translate.api.cloud.yandex.net/translate/v2/translate";

    private final RestTemplate restTemplate;

    private final HttpHeaders headers;

    private Map<String, Object> requestBody;

    private final ObjectMapper objectMapper;

    public RestTemplateHandler() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        objectMapper = new ObjectMapper();
    }

    private void loadHeaders() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(HttpKeysConstants.AUTHORIZATION_KEY.toString(),  String.format(HttpKeysConstants.API_KEY_FORMAT.toString(), token));
    }

    private void buildRequest(String sourceLanguage, String targetLanguage, String sentence) {
        requestBody = new HashMap<>();
        requestBody.put(HttpKeysConstants.SOURCE_LANGUAGE_KEY.toString(), sourceLanguage);
        requestBody.put(HttpKeysConstants.TARGET_LANGUAGE_KEY.toString(), targetLanguage);
        requestBody.put(HttpKeysConstants.SENTENCE_TRANSLATE_KEY.toString(), new String[] {sentence});
    }

    synchronized public ResponseEntity<String> POST(String sourceLanguage, String targetLanguage, String sentence) throws RestTemplateHandlerException {
        loadHeaders();
        buildRequest(sourceLanguage, targetLanguage, sentence);

        String body;

        try {
            body = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RestTemplateHandlerException(e);
        }

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(source, HttpMethod.POST, entity, String.class);
    }
}
