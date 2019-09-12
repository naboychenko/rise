package ru.mentoring.rise.feedbackservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mentoring.rise.feedbackservice.domain.HelloWorld;

import java.util.List;

public interface HelloWorldRepository extends MongoRepository<HelloWorld, String> {
//    Create
//    Read
//    Update
//    Delete
}
