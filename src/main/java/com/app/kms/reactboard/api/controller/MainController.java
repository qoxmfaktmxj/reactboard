package com.app.kms.reactboard.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MainController {
    @GetMapping("/api")
    public List<Object> index(){
        return Arrays.asList("api","테스트");
    }
}
