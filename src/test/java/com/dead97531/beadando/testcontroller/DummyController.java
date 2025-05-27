package com.dead97531.beadando.testcontroller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test-exception")
public class DummyController {

    @GetMapping
    public String throwException() {
        throw new IllegalStateException("Test error");
    }
}