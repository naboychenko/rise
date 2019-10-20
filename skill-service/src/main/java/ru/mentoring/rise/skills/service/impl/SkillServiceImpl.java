package ru.mentoring.rise.skills.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.converter.SkillConverter;
import ru.mentoring.rise.skills.domain.Skill;
import ru.mentoring.rise.skills.exception.NotFoundException;
import ru.mentoring.rise.skills.exception.NotProcessableEntityException;
import ru.mentoring.rise.skills.repo.jpa.SkillRepo;
import ru.mentoring.rise.skills.repo.mongo.MongoSkillRepo;
import ru.mentoring.rise.skills.service.SkillService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SkillServiceImpl implements SkillService {

    private final SkillRepo repo;
    private final MongoSkillRepo mongoSkillRepo;
    private final SkillConverter converter;

    @Override
    public List<SkillDto> getAll() {
        return converter.toDtoList(repo.findAll());
    }

    @Override
    public SkillDto get(Long entityId) throws NotFoundException {
        Optional<Skill> entity = repo.findById(entityId);
        return entity.map(converter::toDto)
                .orElseThrow(() -> new NotFoundException("skill with id " + entityId + " not found"));
    }

    @Override
    @Transactional
    public SkillDto save(SkillDto dto) {
        if (dto == null) {
            throw new NotProcessableEntityException("Entity is null");
        }

        Skill savedEntity = repo.save(converter.toEntity(dto));
        log.info("postgres: saved " + savedEntity.getSkillName());
        mongoSkillRepo.insert(savedEntity);
        log.info("mongo: saved " + savedEntity.getSkillName());
        return converter.toDto(savedEntity);
    }

    @Override
    @Transactional
    public List<SkillDto> save(List<SkillDto> dtoList) {
        if (dtoList == null) {
            throw new NotProcessableEntityException("Entities is null");
        }

        List<Skill> skills = converter.toEntityList(dtoList);
        return converter.toDtoList(repo.saveAll(skills));
    }

    @Override
    @Transactional
    public SkillDto update(Long id, SkillDto dto) throws IllegalArgumentException, NotFoundException {

        if (!exists(id)) {
            throw new NotFoundException("skill with id " + id + " not found");
        }

        Skill updatedSkill = converter.toEntity(dto);
        updatedSkill.setId(id);
        Skill savedEntity = repo.save(updatedSkill);
        log.info("postgres: updated " + savedEntity.getId() + " " + savedEntity.getSkillName());
        mongoSkillRepo.save(savedEntity);
        log.info("mongo: updated " + savedEntity.getId() + " " + savedEntity.getSkillName());
        return converter.toDto(savedEntity);
    }

    @Override
    @Transactional
    public SkillDto delete(Long entityId) throws NotFoundException {
        SkillDto entity = get(entityId);

        repo.deleteById(entityId);
        log.info("postgres: deleted " + entityId);
        mongoSkillRepo.deleteById(entity.getId().toString());
        log.info("mongo: deleted " + entityId);

        return entity;
    }

    @Override
    public boolean exists(Long entityId) {
        return repo.existsById(entityId);
    }
}

