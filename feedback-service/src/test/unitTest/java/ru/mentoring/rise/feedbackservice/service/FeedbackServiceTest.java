package ru.mentoring.rise.feedbackservice.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mentoring.rise.common.dto.FeedbackDto;
import ru.mentoring.rise.common.testutils.generator.dto.FeedbackDtoGenerator;
import ru.mentoring.rise.feedbackservice.domain.Feedback;
import ru.mentoring.rise.feedbackservice.exception.NotFoundException;
import ru.mentoring.rise.feedbackservice.mapper.FeedbackConverter;
import ru.mentoring.rise.feedbackservice.mapper.FeedbackConverterImpl;
import ru.mentoring.rise.feedbackservice.repository.FeedbackRepository;
import ru.mentoring.rise.feedbackservice.service.implementation.FeedbackServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FeedbackServiceTest {

    private static final String ID_FIELD = "id";

    @MockBean
    private FeedbackRepository repository;
    @Autowired
    private FeedbackConverter converter;
    @Autowired
    private FeedbackService service;
    @Autowired
    private FeedbackDtoGenerator feedbackDtoGenerator;
    @Autowired
    private ObjectMapper mapper;

    private String generatedId;
    private FeedbackDto feedbackDtoWithId;
    private FeedbackDto feedbackDtoWithNoId;
    private FeedbackDto feedbackDtoWithGeneratedId;
    private List<FeedbackDto> emptyFeedbackDtoList;
    private List<FeedbackDto> feedbackDtoList;
    private Feedback feedbackWithGeneratedId;
    private List<Feedback> emptyFeedbackList;
    private List<Feedback> feedbackList;

    @Before
    public void setUp() throws IOException {
        generatedId = feedbackDtoGenerator.generateString();

        feedbackDtoWithId = feedbackDtoGenerator.generate();
        feedbackDtoWithNoId = mapper.readValue(mapper.writeValueAsString(feedbackDtoWithId), FeedbackDto.class);
        feedbackDtoWithNoId.setId(null);

        feedbackWithGeneratedId = mapper.readValue(mapper.writeValueAsString(feedbackDtoWithNoId), Feedback.class);
        feedbackWithGeneratedId.setId(generatedId);

        feedbackDtoWithGeneratedId = mapper.readValue(mapper.writeValueAsString(feedbackWithGeneratedId), FeedbackDto.class);

        emptyFeedbackList = new ArrayList<>();
        feedbackList = new ArrayList<>();
        feedbackList.add(feedbackWithGeneratedId);

        emptyFeedbackDtoList = new ArrayList<>();
        feedbackDtoList = new ArrayList<>();
        feedbackDtoList.add(feedbackDtoWithGeneratedId);
    }

    @Test
    public void create_withFeedbackDtoWithNoId_shouldReturnFeedbackDtoWithGeneratedId() {
        when(repository.save(any(Feedback.class))).thenAnswer(i -> i.getArguments()[0]);

        assertThat(service.create(feedbackDtoWithNoId))
                .isNotNull()
                .isEqualToIgnoringGivenFields(feedbackDtoWithGeneratedId, ID_FIELD)
                .extracting(ID_FIELD).isNotNull();
    }

    @Test
    public void create_withFeedbackDtoWithId_shouldReturnFeedbackDtoWithGeneratedId() {
        when(repository.save(any(Feedback.class))).thenAnswer(i -> i.getArguments()[0]);

        assertThat(service.create(feedbackDtoWithId))
                .isNotNull()
                .isEqualToIgnoringGivenFields(feedbackDtoWithGeneratedId, ID_FIELD)
                .extracting(ID_FIELD).isNotNull();
    }

    @Test
    public void update_withNonExistingId_shouldThrowNotFoundException() {
        when(repository.existsById(generatedId)).thenReturn(false);

        assertThatThrownBy(() -> service.update(generatedId, feedbackDtoWithNoId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void update_withGivenIdAndFeedbackDtoWithId_shouldReturnFeedbackDtoWithGivenId() {
        when(repository.existsById(generatedId)).thenReturn(true);
        when(repository.save(feedbackWithGeneratedId)).thenReturn(feedbackWithGeneratedId);

        assertThat(service.update(generatedId, feedbackDtoWithId))
                .isNotNull()
                .isEqualToComparingFieldByFieldRecursively(feedbackDtoWithGeneratedId);
    }

    @Test
    public void update_withGivenIdAndFeedbackDtoWithNoId_shouldReturnFeedbackDtoWithGivenId() {
        when(repository.existsById(generatedId)).thenReturn(true);
        when(repository.save(feedbackWithGeneratedId)).thenReturn(feedbackWithGeneratedId);

        assertThat(service.update(generatedId, feedbackDtoWithNoId))
                .isNotNull()
                .isEqualToComparingFieldByFieldRecursively(feedbackDtoWithGeneratedId);
    }

    @Test
    public void getById_withExistingId_shouldReturnFeedbackDto() {
        when(repository.findById(generatedId)).thenReturn(Optional.of(feedbackWithGeneratedId));

        assertThat(service.getById(feedbackDtoWithGeneratedId.getId()))
                .isNotNull()
                .isEqualToComparingFieldByFieldRecursively(feedbackDtoWithGeneratedId);
    }

    @Test
    public void getById_withNonExistingId_shouldThrowNotFoundException() {
        when(repository.findById(generatedId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(generatedId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    public void getAll_shouldReturnFeedbackDtoList() {
        when(repository.findAll()).thenReturn(feedbackList);

        assertThat(service.getAll())
                .isNotNull()
                .isEqualTo(feedbackDtoList);
    }

    @Test
    public void getAllByGoalId_withExistingGoalId_shouldReturnFeedbackDtoList() {
        when(repository.findAllByGoalId(generatedId)).thenReturn(feedbackList);

        assertThat(service.getAllByGoalId(generatedId))
                .isNotNull()
                .isEqualTo(feedbackDtoList);
    }

    @Test
    public void getAllByGoalId_withNonExistingGoalId_shouldReturnEmptyFeedbackDtoList() {
        when(repository.findAllByGoalId(generatedId)).thenReturn(emptyFeedbackList);

        assertThat(service.getAllByGoalId(generatedId))
                .isNotNull()
                .isEqualTo(emptyFeedbackDtoList);
    }

    @Test
    public void getAllByGoalUserId_withExistingGoalUserId_shouldReturnFeedbackDtoList() {
        when(repository.findAllByGoalUserId(generatedId)).thenReturn(feedbackList);

        assertThat(service.getAllByGoalUserId(generatedId))
                .isNotNull()
                .isEqualTo(feedbackDtoList);
    }

    @Test
    public void getAllByGoalUserId_withNonExistingGoalUserId_shouldReturnEmptyFeedbackDtoList() {
        when(repository.findAllByGoalUserId(generatedId)).thenReturn(emptyFeedbackList);

        assertThat(service.getAllByGoalUserId(generatedId))
                .isNotNull()
                .isEqualTo(emptyFeedbackDtoList);
    }

    @Test
    public void getAllByAuthorId_withExistingAuthorId_shouldReturnFeedbackDtoList() {
        when(repository.findAllByGoalUserId(generatedId)).thenReturn(feedbackList);

        assertThat(service.getAllByGoalUserId(generatedId))
                .isNotNull()
                .isEqualTo(feedbackDtoList);
    }

    @Test
    public void getAllByAuthorId_withNonExistingAuthorId_shouldReturnEmptyFeedbackDtoList() {
        when(repository.findAllByGoalUserId(generatedId)).thenReturn(emptyFeedbackList);

        assertThat(service.getAllByGoalUserId(generatedId))
                .isNotNull()
                .isEqualTo(emptyFeedbackDtoList);
    }

    @Test
    public void deleteById_WithExistingId_shouldReturnDeletedFeedbackDto() {
        when(repository.findById(generatedId)).thenReturn(Optional.of(feedbackWithGeneratedId));

        assertThat(service.deleteById(generatedId))
                .isNotNull()
                .isEqualToComparingFieldByFieldRecursively(feedbackDtoWithGeneratedId);
    }

    @Test
    public void deleteById_withNonExistingId_shouldThrowNotFoundException() {
        when(repository.findById(generatedId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteById(generatedId))
                .isInstanceOf(NotFoundException.class);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        FeedbackService feedbackService() {
            return new FeedbackServiceImpl();
        }

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

        @Bean
        FeedbackConverter feedbackConverter() {
            return new FeedbackConverterImpl();
        }
    }
}