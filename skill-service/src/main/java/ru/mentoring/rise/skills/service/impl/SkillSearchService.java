package ru.mentoring.rise.skills.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mentoring.rise.skills.domain.Skill;
import ru.mentoring.rise.skills.repo.mongo.MongoSkillRepo;
import ru.mentoring.rise.skills.service.SearchService;

@Service
@RequiredArgsConstructor
public class SkillSearchService implements SearchService {

    private final MongoSkillRepo mongoSkillRepo;

    @Override
    public List<Skill> getSkillsByName(String name) {
        return mongoSkillRepo.findBySkillNameRegex(name);
    }

    @Override
    public List<Skill> getSkillsByRank(String rank) {
        return mongoSkillRepo.findByRank(rank.toUpperCase());
    }
}
