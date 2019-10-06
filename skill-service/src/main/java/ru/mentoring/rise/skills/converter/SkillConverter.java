package ru.mentoring.rise.skills.converter;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.domain.Skill;

@Mapper(componentModel = "spring")
public interface SkillConverter {

    SkillConverter INSTANCE = Mappers.getMapper(SkillConverter.class);

    SkillDto skillToSkillDto(Skill skill);

    Skill skillDtoToSkill(SkillDto skillDto);

    List<SkillDto> skillListToSkillDtoList(List<Skill> skillList);

    List<Skill> skillDtoListToSkillList(List<SkillDto> skillDtoList);
}
