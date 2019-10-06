package ru.mentoring.rise.skills.controller;

import java.util.List;
import javassist.NotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.converter.SkillConverter;
import ru.mentoring.rise.skills.domain.Skill;
import ru.mentoring.rise.skills.service.impl.SkillCrudService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SkillController {

    private final SkillCrudService crudService;
    private final SkillConverter converter;

    @GetMapping(value = "{id}")
    public SkillDto getSkillById(@PathVariable Long id) throws NotFoundException {
        return converter.skillToSkillDto(crudService.get(id));
    }

    @GetMapping
    public List<SkillDto> getSkills() {
        return converter.skillListToSkillDtoList(crudService.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto addSkill(@RequestBody SkillDto skillDto) {
        Skill createdSkill = converter.skillDtoToSkill(skillDto);
        return converter.skillToSkillDto(crudService.save(createdSkill));
    }

    @PutMapping(value = "{id}")
    public SkillDto updateSkill(@PathVariable Long id, @Valid @RequestBody SkillDto skillDto) throws NotFoundException {
        Skill updatedSkill = converter.skillDtoToSkill(skillDto);
        return converter.skillToSkillDto(crudService.update(id, updatedSkill));
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSkill(@PathVariable Long id) throws NotFoundException {
        crudService.delete(id);
    }
}
