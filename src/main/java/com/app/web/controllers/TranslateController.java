package com.app.web.controllers;

import com.app.database.exceptions.DataBaseAPIException;
import com.app.database.services.DataBaseAPI;
import com.app.translator.exceptions.TranslatorAPIException;
import com.app.translator.services.TranslatorAPI;
import com.app.web.entities.TranslateRequest;
import com.app.web.entities.TranslateResponse;
import com.app.web.enums.TranslatedSentenceExceptionConstants;
import com.app.web.utils.InputTranslateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TranslateController {

    private final InputTranslateValidator validator;

    private final TranslatorAPI translator;

    private final DataBaseAPI database;

    @Autowired
    public TranslateController(TranslatorAPI translator, DataBaseAPI database, InputTranslateValidator validator) {
        this.validator = validator;
        this.translator = translator;
        this.database = database;
    }

    @GetMapping("/")
    public String translator() {
        return "translator";
    }

    @PostMapping("/translate")
    @ResponseBody
    public TranslateResponse translateSentence(@RequestBody TranslateRequest request) {
        String sourceLanguage = validator.validateSpaces(request.getSourceLanguage()),
                targetLanguage = validator.validateSpaces(request.getTargetLanguage()),
                sentence = validator.validateSpaces(request.getSentence()),
                ipAddress = validator.validateSpaces(request.getIpAddress());

        TranslateResponse translateSentence = new TranslateResponse();

        if (!validator.isValidLanguage(sourceLanguage))
            translateSentence.setTranslation(TranslatedSentenceExceptionConstants.INVALID_INPUT_SOURCE_LANGUAGE.toString());
        else if (!validator.isValidLanguage(targetLanguage))
            translateSentence.setTranslation(TranslatedSentenceExceptionConstants.INVALID_INPUT_TARGET_LANGUAGE.toString());
        else if (!validator.isValidSentence(sentence))
            translateSentence.setTranslation(TranslatedSentenceExceptionConstants.INVALID_SENTENCE.toString());
        else {
            try {
                String translatedSentence = translator.translate(sourceLanguage, targetLanguage, sentence);

                translateSentence.setTranslation("http 200 ".concat(translatedSentence));

                database.save(ipAddress, sentence, translatedSentence);
            } catch (TranslatorAPIException | DataBaseAPIException e) {
                translateSentence.setTranslation(TranslatedSentenceExceptionConstants.INVALID_RESPONSE.toString());
            }
        }

        return translateSentence;
    }
}
