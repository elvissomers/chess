package com.practice.project.chess.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@CrossOrigin(maxAge = 3600)
public class WebTestController {

    @GetMapping("view")
    public String doesItRun() {
        return "It is running!";
    }
}
