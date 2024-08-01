package com.app.web.controllers;

import com.app.web.entity.TranslateRequest;
import com.app.web.entity.TranslateResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserActivityController {

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
