package com.app.web.controllers;

import com.app.database.services.DataBaseAPI;
import com.app.translator.services.TranslatorAPI;
import com.app.web.entities.TranslateRequest;
import com.app.web.entities.TranslateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TranslateController {

    private final TranslatorAPI translator;

    private final DataBaseAPI database;

    @Autowired
    public TranslateController(TranslatorAPI translator, DataBaseAPI database) {
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
        return new TranslateResponse(request.getSentence() + "(перевод)");
    }
}
