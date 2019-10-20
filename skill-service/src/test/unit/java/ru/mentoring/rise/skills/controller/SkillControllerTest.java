package ru.mentoring.rise.skills.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.common.testutils.generator.dto.SkillDtoGenerator;
import ru.mentoring.rise.skills.service.SkillService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("Skill controller unit tests")
@SpringJUnitConfig(ControllerUnitTestConfig.class)
@Tag("unit")
class SkillControllerTest {

    private static final String NOVICE = "NOVICE";

    @Autowired
    private SkillDtoGenerator skillDtoGenerator;
    @Autowired
    private ObjectMapper mapper;

    @Mock
    private SkillService crudService;

    private SkillController controller;

    private SkillDto skillDto;
    private List<SkillDto> skillDtoList;

    @BeforeEach
    void setUp() {
        controller = new SkillController(crudService);

        skillDto = skillDtoGenerator.generate();
        skillDto.setRank(NOVICE);

        skillDtoList = new ArrayList<>();
        skillDtoList.add(skillDto);
    }

    @DisplayName("Get all Skills")
    @Test
    void getSkills() {
        when(crudService.getAll()).thenReturn(skillDtoList);
        assertEquals(controller.getSkills(), skillDtoList);
        verify(crudService).getAll();
    }

    @DisplayName("Create a new Skill")
    @Test
    void createSkill() {
        when(crudService.save(skillDto)).thenReturn(skillDto);
        assertEquals(controller.addSkill(skillDto), skillDto);

        verify(crudService).save(skillDto);
    }

    @DisplayName("Update a Skill")
    @Test
    void updateSkill() throws NotFoundException {
        doReturn(skillDto).when(crudService).update(skillDto.getId(), skillDto);
        SkillDto updatedSkill = controller.updateSkill(skillDto.getId(), skillDto);

        assertEquals(skillDto, updatedSkill);

        verify(crudService).update(skillDto.getId(), skillDto);
    }

    @DisplayName("Get a skill")
    @Test
    void getSkill() throws NotFoundException {
        doReturn(skillDto).when(crudService).get(anyLong());
        assertEquals(controller.getSkillById(skillDto.getId()), skillDto);

        verify(crudService).get(skillDto.getId());
    }

    @DisplayName("Delete a skill")
    @Test
    void deleteSkill() throws NotFoundException {
        doReturn(skillDto).when(crudService).delete(anyLong());
        assertEquals(controller.deleteSkill(skillDto.getId()), skillDto);

        verify(crudService).delete(skillDto.getId());
    }
}
