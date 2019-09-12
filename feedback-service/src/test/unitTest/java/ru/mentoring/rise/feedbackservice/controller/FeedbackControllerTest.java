package ru.mentoring.rise.feedbackservice.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.mentoring.rise.common.dto.FeedbackDto;
import ru.mentoring.rise.common.testutils.generator.dto.FeedbackDtoGenerator;
import ru.mentoring.rise.feedbackservice.exception.NotFoundException;
import ru.mentoring.rise.feedbackservice.service.FeedbackService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FeedbackControllerTest {

    private static final String FEEDBACK = "/feedback";
    private static final String FEEDBACK_ID = "/feedback/{id}";
    private static final String GOALS_ID_FEEDBACK = "/goals/{id}/feedback";
    private static final String USERS_ID_AUTHORED_FEEDBACK = "/users/{id}/authored-feedback/";
    private static final String USERS_ID_FEEDBACK = "/users/{id}/feedback/";

    @MockBean
    private FeedbackService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private FeedbackDtoGenerator feedbackDtoGenerator;
    @Autowired
    private ObjectMapper mapper;

    private String generatedId;
    private FeedbackDto feedbackDtoWithId;
    private FeedbackDto feedbackDtoWithNoId;
    private FeedbackDto feedbackDtoWithGeneratedId;
    private FeedbackDto updatedFeedbackDtoWithId;
    private FeedbackDto updatedFeedbackDtoWithGeneratedId;
    private List<FeedbackDto> feedbackDtoList;
    private List<FeedbackDto> emptyFeedbackDtoList;
    private String feedbackId;
    private String goalId;
    private String goalUserId;
    private String feedbackAuthorId;

    @Before
    public void setUp() throws IOException {
        generatedId = feedbackDtoGenerator.generateString();

        feedbackDtoWithId = feedbackDtoGenerator.generate();
        feedbackDtoWithNoId = mapper.readValue(mapper.writeValueAsString(feedbackDtoWithId), FeedbackDto.class);
        feedbackDtoWithNoId.setId(null);
        feedbackDtoWithGeneratedId = mapper.readValue(mapper.writeValueAsString(feedbackDtoWithNoId), FeedbackDto.class);
        feedbackDtoWithGeneratedId.setId(generatedId);

        updatedFeedbackDtoWithId = feedbackDtoGenerator.generate();
        updatedFeedbackDtoWithGeneratedId = mapper.readValue(mapper.writeValueAsString(updatedFeedbackDtoWithId), FeedbackDto.class);
        updatedFeedbackDtoWithGeneratedId.setId(generatedId);

        feedbackDtoList = new ArrayList<>();
        feedbackDtoList.add(feedbackDtoWithId);

        emptyFeedbackDtoList = new ArrayList<>();

        feedbackId = feedbackDtoWithId.getId();
        goalId = feedbackDtoWithId.getGoal().getId();
        goalUserId = feedbackDtoWithId.getGoal().getUser().getId();
        feedbackAuthorId = feedbackDtoWithId.getAuthor().getId();
    }

    @Test
    public void getFeedback_shouldReturnOkAndFeedbackDtoList() throws Exception {
        when(service.getAll()).thenReturn(feedbackDtoList);

        mvc.perform(get(FEEDBACK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id").value(feedbackId));
    }

    @Test
    public void getFeedbackId_withExistingId_shouldReturnOkAndFeedbackDto() throws Exception {
        when(service.getById(feedbackId)).thenReturn(feedbackDtoWithId);

        mvc.perform(get(FEEDBACK_ID, feedbackId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackDtoWithId.getId()));
    }

    @Test
    public void getFeedbackId_withNonExistingId_shouldReturnNotFound() throws Exception {
        when(service.getById(feedbackId)).thenThrow(new NotFoundException());

        mvc.perform(get(FEEDBACK_ID, feedbackId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getGoalsIdFeedback_withExistingId_shouldReturnOkAndFeedbackDtoList() throws Exception {
        when(service.getAllByGoalId(goalId)).thenReturn(feedbackDtoList);

        mvc.perform(get(GOALS_ID_FEEDBACK, goalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].goal.id").value(goalId));
    }

    @Test
    public void getGoalsIdFeedback_withNonExistingId_shouldReturnOkAndEmptyFeedbackDtoList() throws Exception {
        when(service.getAllByGoalId(goalId)).thenReturn(emptyFeedbackDtoList);

        mvc.perform(get(GOALS_ID_FEEDBACK, goalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void getUsersIdFeedback_withExistingId_shouldReturnOkAndFeedbackDtoList() throws Exception {
        when(service.getAllByGoalUserId(goalUserId)).thenReturn(feedbackDtoList);

        mvc.perform(get(USERS_ID_FEEDBACK, goalUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].goal.user.id").value(goalUserId));
    }

    @Test
    public void getUsersIdFeedback_withNonExistingId_shouldReturnOkAndEmptyFeedbackDtoList() throws Exception {
        when(service.getAllByGoalUserId(goalUserId)).thenReturn(emptyFeedbackDtoList);

        mvc.perform(get(USERS_ID_FEEDBACK, goalUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void getUsersIdAuthoredFeedback_withExistingId_shouldReturnOkAndFeedbackDtoList() throws Exception {
        when(service.getAllByAuthorId(feedbackAuthorId)).thenReturn(feedbackDtoList);

        mvc.perform(get(USERS_ID_AUTHORED_FEEDBACK, feedbackAuthorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].author.id").value(feedbackAuthorId));
    }

    @Test
    public void getUsersIdAuthoredFeedback_withNonExistingId_shouldReturnOkAndEmptyFeedbackDtoList() throws Exception {
        when(service.getAllByAuthorId(feedbackAuthorId)).thenReturn(emptyFeedbackDtoList);

        mvc.perform(get(USERS_ID_AUTHORED_FEEDBACK, feedbackAuthorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void postFeedback_withoutId_shouldReturnCreatedAndFeedbackDto() throws Exception {
        when(service.create(feedbackDtoWithNoId)).thenReturn(feedbackDtoWithId);

        mvc.perform(post(FEEDBACK)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(feedbackDtoWithNoId)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(header().stringValues("location", hasItems(containsString(FEEDBACK + "/" + feedbackId))))
                .andExpect(jsonPath("$.id").value(feedbackId));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void postFeedback_withId_shouldReturnCreatedAndFeedbackDto() throws Exception {
        when(service.create(feedbackDtoWithId)).thenReturn(feedbackDtoWithGeneratedId);

        mvc.perform(post(FEEDBACK)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(feedbackDtoWithId)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andExpect(header().stringValues("location", hasItems(containsString(FEEDBACK + "/" + generatedId))))
                .andExpect(jsonPath("$.id").value(generatedId));
    }

    @Test
    public void putFeedback_withNonExistingId_shouldReturnNotFound() throws Exception {
        when(service.update(generatedId, feedbackDtoWithNoId)).thenThrow(new NotFoundException());

        mvc.perform(put(FEEDBACK_ID, generatedId)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(feedbackDtoWithNoId)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void putFeedback_withExistingId_shouldReturnOkAndFeedbackDtoWithGivenId() throws Exception {
        when(service.update(generatedId, updatedFeedbackDtoWithId)).thenReturn(updatedFeedbackDtoWithGeneratedId);

        mvc.perform(put(FEEDBACK_ID, generatedId)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(updatedFeedbackDtoWithId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(generatedId))
                .andExpect(jsonPath("$.description").value(updatedFeedbackDtoWithGeneratedId.getDescription()));
    }

    @Test
    public void delete_withExistingId_shouldReturnOkAndDeletedFeedbackDto() throws Exception {
        when(service.deleteById(feedbackId)).thenReturn(feedbackDtoWithId);

        mvc.perform(delete(FEEDBACK_ID, feedbackId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(feedbackId));
    }

    @Test
    public void delete_withNonExistingId_shouldReturnNotFound() throws Exception {
        doThrow(NotFoundException.class).when(service).deleteById(feedbackId);

        mvc.perform(delete(FEEDBACK_ID, feedbackId))
                .andExpect(status().isNotFound());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        FeedbackDtoGenerator feedbackDtoGenerator() {
            return new FeedbackDtoGenerator();
        }

        @Bean
        ObjectMapper objectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper;
        }
    }
}