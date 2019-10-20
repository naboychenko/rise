package ru.mentoring.rise.skills.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class SkillControllerIntegrationTest extends SkillBaseIntegrationTest {

    private static final String BASE_URL = "/";
    private static final long NOT_EXISTED_ID = -1L;
    private static final int LEVEL_MORE_THAN_MAXIMAL = 11;

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
                .map(it -> given()
                        .contentType("application/json")
                        .when()
                        .body(it)
                        .post(BASE_URL)
                        .then().extract().as(SkillDto.class))
                .collect(Collectors.toList());
    }

    @Test
    void getAllSkills_Success_ReturnOk() {
        Response response = given()
                .when()
                .get(BASE_URL);

        List<SkillDto> actualSkills = response.body().jsonPath().getList(".", SkillDto.class);
        response
                .then()
                .statusCode(SC_OK);

        assertTrue(actualSkills.containsAll(skillDtos));
    }

    @Test
    void getSkill_Success_ReturnOk() {
        Response response = given()
                .when()
                .get(BASE_URL + skillDtos.get(1).getId());
        SkillDto actualSkill = response.body().as(SkillDto.class);
        response
                .then()
                .statusCode(SC_OK);
        assertEquals(actualSkill, skillDtos.get(1));
    }

    @Test
    void getSkill_NotFound_ExceptionThrown() {
        given()
                .when()
                .get(BASE_URL + NOT_EXISTED_ID)
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    void createSkill_Success_Created() {
        SkillDto newSkill = generator.generate();

        Response response = given()
                .contentType("application/json")
                .when()
                .body(newSkill)
                .post(BASE_URL);

        response.then().statusCode(SC_CREATED);
        SkillDto actualSkill = response.getBody().as(SkillDto.class);
        assertNotNull(actualSkill.getId());
        assertNotEquals(actualSkill.getId(), newSkill.getId());
    }

    //todo enable me when Exception Handler will be implemented
    @Disabled
    @Test
    void createSkill_LevelValidationFailed_ExceptionThrown() {
        SkillDto newSkill = generator.generate();
        newSkill.setLevel(LEVEL_MORE_THAN_MAXIMAL);
        given()
                .contentType("application/json")
                .when()
                .body(newSkill)
                .post(BASE_URL)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    void deleteSkill_Success_NoContent() {
        SkillDto skillToDelete = given()
                .contentType("application/json")
                .when()
                .body(generator.generate())
                .post(BASE_URL)
                .then().extract().as(SkillDto.class);
        given()
                .when()
                .delete(BASE_URL + skillToDelete.getId())
                .then()
                .statusCode(SC_NO_CONTENT);
    }

    @Test
    void deleteSkill_notFound_ExceptionThrown() {
        SkillDto skillToDelete = generator.generate();
        given()
                .when()
                .delete(BASE_URL + skillToDelete.getId())
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    void updateSkill_Success_ReturnOk() {
        SkillDto skill = given()
                .contentType("application/json")
                .when()
                .body(generator.generate())
                .post(BASE_URL)
                .then().extract().as(SkillDto.class);

        Long skillId = skill.getId();
        SkillDto skillToUpdate = generator.generate();

        Response response = given()
                .contentType("application/json")
                .when()
                .body(skillToUpdate)
                .put(BASE_URL + skillId);

        response.then().statusCode(SC_OK);

        SkillDto actualSkill = response.getBody().as(SkillDto.class);

        assertEquals(actualSkill.getId(), skillId);
        assertEquals(actualSkill.getSkillName(), skillToUpdate.getSkillName());
    }

    @Test
    void updateSkill_NotFound_ExceptionThrown() {
        given()
                .contentType("application/json")
                .when()
                .body(generator.generate())
                .put(BASE_URL + NOT_EXISTED_ID)
                .then()
                .statusCode(SC_NOT_FOUND);
    }
}
