package ru.mentoring.rise.feedbackservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mentoring.rise.feedbackservice.domain.HelloWorld;
import ru.mentoring.rise.feedbackservice.service.HelloWorldService;

import java.util.List;

@RestController
@RequestMapping("/helloworld")
public class HelloWorldController {

    @Autowired
    private HelloWorldService service;

    @GetMapping
    public List<HelloWorld> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public HelloWorld getById(@PathVariable("id") String id) {
        return service.getById(id);
    }

    @PostMapping
    public HelloWorld putHelloWorld(@RequestBody HelloWorld helloWorld) {
        return service.save(helloWorld);
    }
}
