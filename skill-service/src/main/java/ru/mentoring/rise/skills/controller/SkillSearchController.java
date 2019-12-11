package ru.mentoring.rise.skills.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.converter.SkillConverter;
import ru.mentoring.rise.skills.service.SearchService;

@RestController
@RequestMapping(SkillSearchController.SKILLS_SEARCH)
@RequiredArgsConstructor
public class SkillSearchController {

    public static final String SKILLS_SEARCH = "/skill/search";
    public static final String FIND_SKILL_BY_NAME = "/{name}";
    public static final String FIND_SKILL_BY_RANK = "/rank/{skillRank}";

    private final SearchService searchService;
    private final SkillConverter converter;

    @GetMapping(FIND_SKILL_BY_NAME)
    public List<SkillDto> findSkillsByName(@PathVariable String name) {

        return converter.toDtoList(searchService.getSkillsByName(name));
    }

    @GetMapping(FIND_SKILL_BY_RANK)
    public List<SkillDto> findSkillsByRank(@PathVariable String skillRank) {
        return converter.toDtoList(searchService.getSkillsByRank(skillRank));
    }
}
