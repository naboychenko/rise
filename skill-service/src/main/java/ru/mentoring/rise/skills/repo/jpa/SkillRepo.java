package ru.mentoring.rise.skills.repo.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mentoring.rise.skills.domain.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Long> {

    boolean existsById(Long id);

    boolean existsBySkillName(String name);

    Skill findSkillById(Long id);

    List<Skill> findSkillBySkillNameIn(List<String> skills);
}
