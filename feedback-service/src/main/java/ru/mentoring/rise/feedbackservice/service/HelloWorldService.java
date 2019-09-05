package ru.mentoring.rise.feedbackservice.service;

import ru.mentoring.rise.feedbackservice.domain.HelloWorld;

import java.util.List;

public interface HelloWorldService {
//    Create
    HelloWorld save(HelloWorld helloWorld);
//    Read
    HelloWorld getById(String id);
    List<HelloWorld> getAll();
//    Update
//    Delete
}
