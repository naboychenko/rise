package ru.mentoring.rise.feedbackservice.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.mentoring.rise.common.dto.FeedbackDto;
import ru.mentoring.rise.common.testutils.generator.dto.FeedbackDtoGenerator;
import ru.mentoring.rise.feedbackservice.repository.FeedbackRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedbackControllerIntegrationTest {

    private static final String FEEDBACK = "/feedback";
    private static final String FEEDBACK_ID = "/feedback/{id}";
    private static final String GOALS_ID_FEEDBACK = "/goals/{id}/feedback";
    private static final String USERS_ID_AUTHORED_FEEDBACK = "/users/{id}/authored-feedback/";
    private static final String USERS_ID_FEEDBACK = "/users/{id}/feedback/";
    private static final String ID_FIELD = "id";
    private static final String[] FIELDS_TO_COMPARE_FEEDBACK_TO_FEEDBACK_DTO = {"author.id", "goal.id", "description"};

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FeedbackRepository repository;
    @Autowired
    private FeedbackDtoGenerator feedbackDtoGenerator;

    private long feedbackSize;
    private String randomId;
    private String feedbackId;
    private String goalId;
    private String goalsUserId;
    private Object feedbackAuthorId;
    private FeedbackDto feedbackDto;
    private FeedbackDto updatedFeedbackDto;
    private ParameterizedTypeReference<List<FeedbackDto>> feedbackDtoListType;

    @Before
    public void setUp() {
        restTemplate.postForLocation(FEEDBACK, feedbackDtoGenerator.generate());
        restTemplate.postForLocation(FEEDBACK, feedbackDtoGenerator.generate());

        feedbackDto = restTemplate.postForObject(FEEDBACK, feedbackDtoGenerator.generate(), FeedbackDto.class);
        feedbackId = feedbackDto.getId();
        goalId = feedbackDto.getGoal().getId();
        goalsUserId = feedbackDto.getGoal().getUser().getId();
        feedbackAuthorId = feedbackDto.getAuthor().getId();

        updatedFeedbackDto = feedbackDtoGenerator.generate();

        feedbackSize = repository.count();
        randomId = feedbackDtoGenerator.generateString();

        feedbackDtoListType = new ParameterizedTypeReference<List<FeedbackDto>>() {
        };
    }

    @Test
    public void getFeedback_shouldReturnOkAndFeedbackDtoList() {
        ResponseEntity<List<FeedbackDto>> listResponseEntity =
                restTemplate.exchange(FEEDBACK, HttpMethod.GET, null, feedbackDtoListType);

        assertThat(listResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(listResponseEntity.getBody())
                .isNotNull();
        assertThat(listResponseEntity.getBody().size())
                .isEqualTo(feedbackSize);
    }

    @Test
    public void getFeedbackId_withExistingId_shouldReturnOkAndFeedbackDto() {
        ResponseEntity<FeedbackDto> dtoResponseEntity = restTemplate.getForEntity(FEEDBACK_ID, FeedbackDto.class, feedbackId);

        assertThat(dtoResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(dtoResponseEntity.getBody())
                .isNotNull();
        assertThat(dtoResponseEntity.getBody().getId())
                .isNotBlank()
                .isEqualTo(feedbackId);
    }

    @Test
    public void getFeedbackId_withNonExistingId_shouldReturnNotFound() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(FEEDBACK_ID, String.class, randomId);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getGoalsIdFeedback_withExistingId_shouldReturnOkAndFeedbackDtoList() {
        ResponseEntity<List<FeedbackDto>> listResponseEntity =
                restTemplate.exchange(GOALS_ID_FEEDBACK, HttpMethod.GET, null, feedbackDtoListType, goalId);

        assertThat(listResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(listResponseEntity.getBody())
                .isNotEmpty();
    }

    @Test
    public void getGoalsIdFeedback_withNonExistingId_shouldReturnOkAndEmptyFeedbackDtoList() {
        ResponseEntity<List<FeedbackDto>> listResponseEntity =
                restTemplate.exchange(GOALS_ID_FEEDBACK, HttpMethod.GET, null, feedbackDtoListType, randomId);

        assertThat(listResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(listResponseEntity.getBody())
                .isEmpty();
    }

    @Test
    public void getUsersIdFeedback_withExistingId_shouldReturnOkAndFeedbackDtoList() {
        ResponseEntity<List<FeedbackDto>> listResponseEntity =
                restTemplate.exchange(USERS_ID_FEEDBACK, HttpMethod.GET, null, feedbackDtoListType, goalsUserId);

        assertThat(listResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(listResponseEntity.getBody())
                .isNotEmpty();
    }

    @Test
    public void getUsersIdFeedback_withNonExistingId_shouldReturnOkAndEmptyFeedbackDtoList() {
        ResponseEntity<List<FeedbackDto>> listResponseEntity =
                restTemplate.exchange(USERS_ID_FEEDBACK, HttpMethod.GET, null, feedbackDtoListType, randomId);

        assertThat(listResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(listResponseEntity.getBody())
                .isEmpty();
    }

    @Test
    public void getUsersIdAuthoredFeedback_withExistingId_shouldReturnOkAndFeedbackDtoList() {
        ResponseEntity<List<FeedbackDto>> listResponseEntity =
                restTemplate.exchange(USERS_ID_AUTHORED_FEEDBACK, HttpMethod.GET, null, feedbackDtoListType, feedbackAuthorId);

        assertThat(listResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(listResponseEntity.getBody())
                .isNotEmpty();
    }

    @Test
    public void getUsersIdAuthoredFeedback_withNonExistingId_shouldReturnOkAndEmptyFeedbackDtoList() {
        ResponseEntity<List<FeedbackDto>> listResponseEntity =
                restTemplate.exchange(USERS_ID_AUTHORED_FEEDBACK, HttpMethod.GET, null, feedbackDtoListType, randomId);

        assertThat(listResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(listResponseEntity.getBody())
                .isEmpty();
    }

    @Test
    public void postFeedback_shouldReturnCreatedAndFeedbackDto() {
        ResponseEntity<FeedbackDto> dtoResponseEntity = restTemplate.postForEntity(FEEDBACK, feedbackDto, FeedbackDto.class);

        assertThat(dtoResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        assertThat(dtoResponseEntity.getBody())
                .isNotNull();
        assertThat(dtoResponseEntity.getBody())
                .isEqualToIgnoringGivenFields(feedbackDto, ID_FIELD);
        assertThat(dtoResponseEntity.getBody().getId())
                .isNotEqualTo(feedbackDto.getId());

        assertThat(repository.count())
                .isEqualTo(feedbackSize + 1);
    }

    @Test
    public void putFeedback_withNonExistingId_shouldReturnNotFound() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(FEEDBACK_ID, HttpMethod.PUT, new HttpEntity<>(feedbackDto), String.class, randomId);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void updateFeedback_withExistingId_shouldReturnOkAndFeedbackDto() {
        ResponseEntity<FeedbackDto> responseEntity =
                restTemplate.exchange(FEEDBACK_ID, HttpMethod.PUT, new HttpEntity<>(updatedFeedbackDto), FeedbackDto.class, feedbackId);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isNotNull();
        assertThat(responseEntity.getBody())
                .isEqualToComparingOnlyGivenFields(updatedFeedbackDto, FIELDS_TO_COMPARE_FEEDBACK_TO_FEEDBACK_DTO);
        assertThat(responseEntity.getBody().getId())
                .isNotEqualTo(updatedFeedbackDto.getId());
    }

    @Test
    public void delete_withExistingId_shouldReturnOkAndFeedbackDto() {
        ResponseEntity<FeedbackDto> responseEntity =
                restTemplate.exchange(FEEDBACK_ID, HttpMethod.DELETE, null, FeedbackDto.class, feedbackId);

        assertThat(repository.count())
                .isEqualTo(feedbackSize - 1);
        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
                .isNotNull()
                .isEqualToComparingFieldByFieldRecursively(feedbackDto);
    }

    @Test
    public void delete_withNonExistingId_shouldReturnNotFound() {
        ResponseEntity<String> responseEntity =
                restTemplate.exchange(FEEDBACK_ID, HttpMethod.DELETE, null, String.class, randomId);

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        FeedbackDtoGenerator feedbackDtoGenerator() {
            return new FeedbackDtoGenerator();
        }
    }

}
