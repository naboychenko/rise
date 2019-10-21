package ru.mentoring.rise.skills.repo.mongo;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.mentoring.rise.skills.domain.Skill;

public interface MongoSkillRepo extends MongoRepository<Skill, String> {

    @Query(value = "{'skillName': {$regex : ?0, $options: 'i'}}")
    List<Skill> findBySkillNameRegex(String name);

    List<Skill> findByRank(String rank);
}
