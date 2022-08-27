package com.genomic.Fibersmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    public HomeController() {}

    @GetMapping("/")
    public String homePage() {
        return "Welcome to Fibersmart";
    }
}
