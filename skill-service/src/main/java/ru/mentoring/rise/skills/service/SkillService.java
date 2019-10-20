package ru.mentoring.rise.skills.service;

import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.mentoring.rise.common.dto.SkillDto;

import java.util.List;

@Transactional(readOnly = true)
public interface SkillService {

    List<SkillDto> getAll();

    SkillDto get(Long entityId) throws NotFoundException;

    @Transactional
    SkillDto save(SkillDto entity);

    @Transactional
    List<SkillDto> save(List<SkillDto> entities);

    @Transactional
    SkillDto update(Long id, SkillDto note) throws IllegalArgumentException, NotFoundException;

    @Transactional
    SkillDto delete(Long entityId) throws NotFoundException;

    boolean exists(Long entityId);
}
