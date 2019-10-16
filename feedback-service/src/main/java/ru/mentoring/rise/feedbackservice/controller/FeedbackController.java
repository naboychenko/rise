package ru.mentoring.rise.feedbackservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mentoring.rise.common.dto.FeedbackDto;
import ru.mentoring.rise.feedbackservice.service.FeedbackService;

import java.net.URI;
import java.util.List;

@RestController
class FeedbackController {

    private static final String FEEDBACK = "/feedback";
    private static final String FEEDBACK_ID = "/feedback/{id}";
    private static final String GOALS_ID_FEEDBACK = "/goals/{id}/feedback";
    private static final String USERS_ID_AUTHORED_FEEDBACK = "/users/{id}/authored-feedback/";
    private static final String USERS_ID_FEEDBACK = "/users/{id}/feedback/";

    @Autowired
    private FeedbackService service;

    @GetMapping(FEEDBACK)
    List<FeedbackDto> getAllFeedback() {
        return service.getAll();
    }

    @GetMapping(FEEDBACK_ID)
    FeedbackDto getFeedbackById(@PathVariable String id) {
        return  service.getById(id);
    }

    @GetMapping(GOALS_ID_FEEDBACK)
    List<FeedbackDto> getAllFeedbackByGoalId(@PathVariable String id) {
        return service.getAllByGoalId(id);
    }

    @GetMapping(USERS_ID_FEEDBACK)
    List<FeedbackDto> getAllFeedbackByGoalUserId(@PathVariable String id) {
        return service.getAllByGoalUserId(id);
    }

    @GetMapping(USERS_ID_AUTHORED_FEEDBACK)
    List<FeedbackDto> getAllFeedbackByAuthorId(@PathVariable String id) {
        return service.getAllByAuthorId(id);
    }

    @PostMapping(FEEDBACK)
    ResponseEntity<FeedbackDto> createFeedback(@RequestBody FeedbackDto feedbackDto) {
        feedbackDto = service.create(feedbackDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(feedbackDto.getId())
                .encode()
                .toUri();

        return ResponseEntity.created(location).body(feedbackDto);
    }

    @PutMapping(FEEDBACK_ID)
    FeedbackDto updateFeedback(@PathVariable String id, @RequestBody FeedbackDto feedbackDto) {
        return service.update(id,feedbackDto);
    }

    @DeleteMapping(FEEDBACK_ID)
    FeedbackDto deleteFeedback(@PathVariable String id) {
        return service.deleteById(id);
    }
}
