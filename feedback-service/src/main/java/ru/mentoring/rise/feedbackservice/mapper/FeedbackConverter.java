package ru.mentoring.rise.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mentoring.rise.common.dto.FeedbackDto;
import ru.mentoring.rise.common.dto.GoalDto;
import ru.mentoring.rise.common.dto.UserDto;
import ru.mentoring.rise.feedbackservice.domain.Feedback;
import ru.mentoring.rise.feedbackservice.domain.Goal;
import ru.mentoring.rise.feedbackservice.domain.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackConverter {

    List<FeedbackDto> feedbackListToFeedbackDtoList(List<Feedback> feedbackList);
    List<Feedback> feedbackDtoListToFeedbackList(List<FeedbackDto> feedbackDtoList);


    FeedbackDto feedbackToFeedbackDto(Feedback feedback);
    Feedback feedbackDtoToFeedback(FeedbackDto feedbackDto);


    @Mapping(target = "name", ignore = true)
    @Mapping(target = "resource", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "feedback", ignore = true)
    GoalDto goalToGoalDto(Goal goal);

    Goal goalDtoToGoal(GoalDto goalDto);


    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "role", ignore = true)
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

}
