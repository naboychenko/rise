package ru.mentoring.rise.skills.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.service.SearchService;

import java.util.List;

@RestController
@RequestMapping(SkillSearchController.SKILLS_SEARCH)
@RequiredArgsConstructor
public class SkillSearchController {

    static final String SKILLS_SEARCH = "/search";

    private static final String FIND_SKILL_BY_NAME = "/{name}";
    private static final String FIND_SKILL_BY_RANK = "/rank/{skillRank}";

    private final SearchService searchService;

    @GetMapping(FIND_SKILL_BY_NAME)
    public List<SkillDto> findSkillsByName(@PathVariable String name) {

        return searchService.getSkillsByName(name);
    }

    @GetMapping(FIND_SKILL_BY_RANK)
    public List<SkillDto> findSkillsByRank(@PathVariable String skillRank) {
        return searchService.getSkillsByRank(skillRank);
    }
}
