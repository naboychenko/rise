package ru.mentoring.rise.feedbackservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mentoring.rise.common.dto.FeedbackDto;
import ru.mentoring.rise.feedbackservice.domain.Feedback;
import ru.mentoring.rise.feedbackservice.exception.NotFoundException;
import ru.mentoring.rise.feedbackservice.mapper.FeedbackConverter;
import ru.mentoring.rise.feedbackservice.repository.FeedbackRepository;
import ru.mentoring.rise.feedbackservice.service.FeedbackService;

import java.util.List;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository repository;

    @Autowired
    private FeedbackConverter converter;

    @Override
    public FeedbackDto create(FeedbackDto feedbackDto) {
        return save(UUID.randomUUID().toString(), feedbackDto);
    }

    @Override
    public FeedbackDto update(String id, FeedbackDto feedbackDto) {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        return save(id, feedbackDto);
    }

    private FeedbackDto save(String id, FeedbackDto feedbackDto) {
        Feedback feedback = converter.feedbackDtoToFeedback(feedbackDto);

        feedback.setId(id);
        feedback = repository.save(feedback);

        return converter.feedbackToFeedbackDto(feedback);
    }

    @Override
    public FeedbackDto getById(String id) {
        Feedback feedback = repository.findById(id).orElseThrow(NotFoundException::new);

        return converter.feedbackToFeedbackDto(feedback);
    }

    @Override
    public List<FeedbackDto> getAll() {
        List<Feedback> feedbackList = repository.findAll();

        return converter.feedbackListToFeedbackDtoList(feedbackList);
    }

    @Override
    public List<FeedbackDto> getAllByGoalId(String id) {
        List<Feedback> feedbackList = repository.findAllByGoalId(id);

        return converter.feedbackListToFeedbackDtoList(feedbackList);
    }

    @Override
    public List<FeedbackDto> getAllByGoalUserId(String id) {
        List<Feedback> feedbackList = repository.findAllByGoalUserId(id);

        return converter.feedbackListToFeedbackDtoList(feedbackList);
    }

    @Override
    public List<FeedbackDto> getAllByAuthorId(String id) {
        List<Feedback> feedbackList = repository.findAllByAuthorId(id);

        return converter.feedbackListToFeedbackDtoList(feedbackList);
    }

    @Override
    public FeedbackDto deleteById(String id) {

        Feedback feedback = repository.findById(id).orElseThrow(NotFoundException::new);
        repository.deleteById(id);

        return converter.feedbackToFeedbackDto(feedback);
    }
}
