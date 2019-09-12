package ru.mentoring.rise.feedbackservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.mentoring.rise.feedbackservice.domain.Feedback;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, String> {

    List<Feedback> findAllByGoalId(String id);
    List<Feedback> findAllByGoalUserId(String id);
    List<Feedback> findAllByAuthorId(String id);

}
