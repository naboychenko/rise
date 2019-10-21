package ru.mentoring.rise.skills.converter;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.mentoring.rise.common.dto.SkillDto;
import ru.mentoring.rise.skills.domain.Skill;

@Mapper(componentModel = "spring")
public interface SkillConverter {

    SkillConverter INSTANCE = Mappers.getMapper(SkillConverter.class);

    SkillDto toDto(Skill skill);

    Skill toEntity(SkillDto skillDto);

    List<SkillDto> toDtoList(List<Skill> skillList);

    List<Skill> toEntityList(List<SkillDto> skillDtoList);
}
