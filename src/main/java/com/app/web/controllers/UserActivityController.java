package com.app.web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserActivityController {

    @GetMapping("/")
    public String translator() {
        return "translator";
    }
}
