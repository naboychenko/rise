package ru.mentoring.rise.skills.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.converter.SkillConverter;
import ru.mentoring.rise.skills.repo.mongo.MongoSkillRepo;
import ru.mentoring.rise.skills.service.SearchService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final MongoSkillRepo mongoSkillRepo;
    private final SkillConverter converter;


    @Override
    public List<SkillDto> getSkillsByName(String name) {
        return converter.toDtoList(mongoSkillRepo.findBySkillNameRegex(name));
    }

    @Override
    public List<SkillDto> getSkillsByRank(String rank) {
        return converter.toDtoList(mongoSkillRepo.findByRank(rank.toUpperCase()));
    }
}
