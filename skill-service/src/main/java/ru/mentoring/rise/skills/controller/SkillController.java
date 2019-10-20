package ru.mentoring.rise.skills.controller;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.service.SkillService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService crudService;

    @GetMapping(value = "{id}")
    public SkillDto getSkillById(@PathVariable Long id) throws NotFoundException {
        return crudService.get(id);
    }

    @GetMapping
    public List<SkillDto> getSkills() {
        return crudService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SkillDto addSkill(@Valid @RequestBody SkillDto skillDto) {
        return crudService.save(skillDto);
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public SkillDto updateSkill(@PathVariable Long id, @Valid @RequestBody SkillDto skillDto) throws NotFoundException {
        return crudService.update(id, skillDto);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SkillDto deleteSkill(@PathVariable Long id) throws NotFoundException {
        return crudService.delete(id);
    }
}
