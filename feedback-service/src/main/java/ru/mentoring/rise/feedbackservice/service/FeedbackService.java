package ru.mentoring.rise.feedbackservice.service;

import ru.mentoring.rise.common.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {

    FeedbackDto create(FeedbackDto feedbackDto);
    FeedbackDto update(String id, FeedbackDto feedbackDto);

    FeedbackDto getById(String id);

    List<FeedbackDto> getAll();
    List<FeedbackDto> getAllByGoalId(String id);
    List<FeedbackDto> getAllByGoalUserId(String id);
    List<FeedbackDto> getAllByAuthorId(String id);

    FeedbackDto deleteById(String id);
}
