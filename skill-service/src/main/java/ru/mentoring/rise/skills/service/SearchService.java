package ru.mentoring.rise.skills.service;

import java.util.List;
import ru.mentoring.rise.skills.domain.Skill;

public interface SearchService {

    List<Skill> getSkillsByName(String name);

    List<Skill> getSkillsByRank(String rank);
}
