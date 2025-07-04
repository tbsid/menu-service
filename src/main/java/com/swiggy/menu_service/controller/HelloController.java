package com.swiggy.menu_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/hello")
@RestController
public class HelloController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        log.info("Hello World endpoint called");
        return "Hello World from the Menu Service!";
    }

}
