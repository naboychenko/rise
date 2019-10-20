package ru.mentoring.rise.skills.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.common.testutils.generator.dto.SkillDtoGenerator;
import ru.mentoring.rise.skills.SkillBaseIntegrationTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkillSearchControllerIntegrationTest extends SkillBaseIntegrationTest {

    private static final String BASE_URL = "/";
    private static final String SEARCH_URL = "/search";
    private static final String FIND_SKILL_BY_RANK = "/rank";
    private static final String JAVA_REST_ASSURED = "JAVA: Rest assured";
    private static final String NOT_EXISTED_SKILL_NAME = "not existed skill";
    private static final String NOT_STORED_RANK = "INTERMEDIATE";

    @LocalServerPort
    int port;

    @Autowired
    private SkillDtoGenerator generator;

    private List<SkillDto> skillDtos;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
        skillDtos = new ArrayList<>();
        skillDtos = Stream
                .generate(() -> generator.generate())
                .limit(3)
                .peek(it -> it.setSkillName(JAVA_REST_ASSURED))
                .map(it -> given()
                        .contentType("application/json")
                        .when()
                        .body(it)
                        .post(BASE_URL)
                        .then().extract().as(SkillDto.class))
                .collect(Collectors.toList());
    }

    @Test
    void findSkillByName_Success_ReturnOk() {
        Response response = given()
                .when()
                .get(SEARCH_URL + "/" + skillDtos.get(1).getSkillName());
        List<SkillDto> actualSkills = response.body().jsonPath().getList(".", SkillDto.class);
        response
                .then()
                .statusCode(SC_OK);
        assertEquals(actualSkills.size(), skillDtos.size());
        assertEquals(actualSkills, skillDtos);
    }

    @Test
    void findSkillByName_NotFound_EmptyListReturned() {
        Response response = given()
                .when()
                .get(SEARCH_URL + "/" + NOT_EXISTED_SKILL_NAME);
        List<SkillDto> actualSkills = response.body().jsonPath().getList(".", SkillDto.class);
        response
                .then()
                .statusCode(SC_OK);
        assertEquals(0, actualSkills.size());
    }

    @Test
    void findSkillByRank_Success_ReturnOk() {
        Response response = given()
                .when()
                .get(SEARCH_URL + FIND_SKILL_BY_RANK + "/" + skillDtos.get(1).getRank());
        List<SkillDto> actualSkills = response.body().jsonPath().getList(".", SkillDto.class);
        response
                .then()
                .statusCode(SC_OK);
        assertTrue(actualSkills.size() >= skillDtos.size());
        assertTrue(actualSkills.containsAll(skillDtos));
    }

    @Test
    void findSkillByRnk_NotFound_EmptyListReturned() {
        Response response = given()
                .when()
                .get(SEARCH_URL + FIND_SKILL_BY_RANK + "/" + NOT_STORED_RANK);
        List<SkillDto> actualSkills = response.body().jsonPath().getList(".", SkillDto.class);
        response
                .then()
                .statusCode(SC_OK);
        assertEquals(0, actualSkills.size());
    }
}
