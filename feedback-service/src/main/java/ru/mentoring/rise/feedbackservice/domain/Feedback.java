package ru.mentoring.rise.feedbackservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Feedback {

    @Id
    private String id;

    private User author;

    private Goal goal;

    private String description;

}
