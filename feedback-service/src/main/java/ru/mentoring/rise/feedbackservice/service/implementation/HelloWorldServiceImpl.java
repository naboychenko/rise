package ru.mentoring.rise.feedbackservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mentoring.rise.feedbackservice.domain.HelloWorld;
import ru.mentoring.rise.feedbackservice.exception.NotFoundException;
import ru.mentoring.rise.feedbackservice.repository.HelloWorldRepository;
import ru.mentoring.rise.feedbackservice.service.HelloWorldService;

import java.util.List;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {

    @Autowired
    private HelloWorldRepository repository;

    @Override
    public HelloWorld save(HelloWorld helloWorld) {
        return repository.save(helloWorld);
    }

    @Override
    public HelloWorld getById(String id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<HelloWorld> getAll() {
        return repository.findAll();
    }
}
