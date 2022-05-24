package com.maoge.jacocopets.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {

    @GetMapping("/greetings")
    public String greetings(){
        return "Hello";
    }

    @GetMapping("/echo")
    public String echo(@RequestParam(required = false) String value) throws Exception {
        if(value == null || value==""){
            throw new Exception("error");
        }
        return value;
    }
}
