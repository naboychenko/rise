package ru.mentoring.rise.skills.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.common.testutils.generator.dto.SkillDtoGenerator;
import ru.mentoring.rise.skills.domain.Skill;
import ru.mentoring.rise.skills.service.SearchService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(ControllerUnitTestConfig.class)
@DisplayName("Skill search controller tests")
@Tag("unit")
class SearchControllerTest {

    private static final String NOVICE = "NOVICE";
    private static final String ADVANCED = "ADVANCED";
    private static final String SKILL_NAME = "skillName";
    private static final String SKILL_RANK = "skillRank";

    @Autowired
    private SkillDtoGenerator skillDtoGenerator;

    @Mock
    private SearchService searchService;

    private SkillSearchController controller;
    private SkillDto skillDto;
    private List<SkillDto> skillDtoList;
    private List<Skill> skillList;

    @BeforeEach
    void setUp() {
        controller = new SkillSearchController(searchService);

        skillDto = skillDtoGenerator.generate();
        skillDto.setRank(NOVICE);

        skillDtoList = new ArrayList<>();
        skillDtoList.add(skillDto);

        skillList = new ArrayList<>();
    }

    @DisplayName("Search by skill name")
    @Test
    void searchSkillByName() {
        when(searchService.getSkillsByName(anyString())).thenReturn(skillDtoList);
        assertEquals(controller.findSkillsByName(SKILL_NAME), skillDtoList);
        verify(searchService).getSkillsByName(SKILL_NAME);
    }
}
