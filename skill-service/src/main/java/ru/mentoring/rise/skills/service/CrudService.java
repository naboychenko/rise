package ru.mentoring.rise.skills.service;

import java.util.List;
import javassist.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.mentoring.rise.skills.domain.Skill;

@Transactional(readOnly = true)
public interface CrudService {

    List<Skill> getAll();

    Skill get(Long entityId) throws NotFoundException;

    @Transactional
    Skill save(Skill entity);

    @Transactional
    List<Skill> save(List<Skill> entities);

    @Transactional
    Skill update(Long id, Skill note) throws IllegalArgumentException, NotFoundException;

    @Transactional
    Skill delete(Long entityId) throws NotFoundException;

    boolean exists(Long entityId);
}
