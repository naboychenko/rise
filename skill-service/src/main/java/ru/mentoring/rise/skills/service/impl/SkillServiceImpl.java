package ru.mentoring.rise.skills.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mentoring.rise.skills.domain.Skill;
import ru.mentoring.rise.skills.exception.NotFoundException;
import ru.mentoring.rise.skills.exception.NotProcessableEntityException;
import ru.mentoring.rise.skills.repo.jpa.SkillRepo;
import ru.mentoring.rise.skills.repo.mongo.MongoSkillRepo;
import ru.mentoring.rise.skills.service.SkillService;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SkillServiceImpl implements SkillService {

    private final SkillRepo repo;
    private final MongoSkillRepo mongoSkillRepo;

    @Override
    public List<Skill> getAll() {
        return repo.findAll();
    }

    @Override
    public Skill get(Long entityId) throws NotFoundException {
        Optional<Skill> entity = repo.findById(entityId);
        return entity.orElseThrow(
            () -> new NotFoundException("skill with id " + entityId + " not found"));
    }

    @Override
    @Transactional
    public Skill save(Skill entity) {
        if (entity == null) {
            throw new NotProcessableEntityException("Entity is null");
        }

        Skill savedEntity = repo.save(entity);
        log.info("postgres: saved " + savedEntity.getSkillName());
        mongoSkillRepo.insert(savedEntity);
        log.info("mongo: saved " + savedEntity.getSkillName());
        return savedEntity;
    }

    @Override
    @Transactional
    public List<Skill> save(List<Skill> entities) {
        if (entities == null) {
            throw new NotProcessableEntityException("Entities is null");
        }

        return repo.saveAll(entities);
    }

    @Override
    @Transactional
    public Skill update(Long id, Skill skill) throws IllegalArgumentException, NotFoundException {

        if (!exists(id)) {
            throw new NotFoundException("skill with id " + id + " not found");
        }

        skill.setId(id);
        Skill savedEntity = repo.save(skill);
        log.info("postgres: updated " + savedEntity.getSkillName());
        mongoSkillRepo.save(savedEntity);
        log.info("mongo: updated " + savedEntity.getSkillName());
        return savedEntity;
    }

    @Override
    @Transactional
    public Skill delete(Long entityId) throws NotFoundException {
        Skill entity = get(entityId);
        repo.deleteById(entityId);
        log.info("postgres: deleted " + entityId);
        mongoSkillRepo.delete(entity);
        log.info("mongo: deleted " + entityId);
        return entity;
    }

    @Override
    public boolean exists(Long entityId) {
        return repo.existsById(entityId);
    }
}

