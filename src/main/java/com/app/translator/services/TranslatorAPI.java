package com.app.translator.services;

import com.app.config.PropertiesManager;
import com.app.translator.entities.Task;
import com.app.translator.exceptions.TranslatorAPIException;
import com.app.translator.http.RestTemplateHandler;
import com.app.translator.parsers.JsonParserAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
@Slf4j
public class TranslatorAPI {

    private static final String POOL_SIZE_KEY = "translator.api.pool.size";

    private static final int DEFAULT_POOL_SIZE = 10;

    private final RestTemplateHandler restTemplateHandler;

    private final JsonParserAPI parser;

    private final PropertiesManager propertiesManager;

    @Autowired
    public TranslatorAPI(RestTemplateHandler restTemplateHandler, JsonParserAPI parser, PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
        this.restTemplateHandler = restTemplateHandler;
        this.parser = parser;
    }

    public String translate(String sourceLanguage, String targetLanguage, String sentence) throws TranslatorAPIException {
        String size = propertiesManager.get(POOL_SIZE_KEY);
        String[] splitSentence = sentence.split(" ");

        ExecutorService pool = Executors.newFixedThreadPool(
                size == null ? DEFAULT_POOL_SIZE : Integer.parseInt(size));

        BlockingQueue<Task> blockSentence = new LinkedBlockingDeque<>();
        for (String split : splitSentence)
            blockSentence.add(new Task(split, sourceLanguage, targetLanguage, restTemplateHandler, parser));

        List<Future<String>> futures = new LinkedList<>();

        while (!blockSentence.isEmpty()) {
            try {
                Task task = blockSentence.take();
                futures.add(pool.submit(() -> {
                    try {
                        return task.call();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }));
            } catch (InterruptedException e) {
                throw new TranslatorAPIException(e);
            }

        }

        StringBuilder result;
        try {
            result = new StringBuilder();
            for (Future<String> future : futures)
                result.append(future.get()).append(' ');
        } catch (InterruptedException | ExecutionException e) {
            throw new TranslatorAPIException(e);
        }

        pool.shutdown();
        return result.toString().trim();
    }
}
