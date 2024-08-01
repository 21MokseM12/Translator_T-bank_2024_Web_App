package com.app.web.controllers;

import com.app.web.entities.TranslateRequest;
import com.app.web.entities.TranslateResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TranslateController {

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
