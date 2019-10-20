package ru.mentoring.rise.skills.service;

import ru.mentoring.rise.common.dto.SkillDto;

import java.util.List;

public interface SearchService {

    List<SkillDto> getSkillsByName(String name);

    List<SkillDto> getSkillsByRank(String rank);
}
