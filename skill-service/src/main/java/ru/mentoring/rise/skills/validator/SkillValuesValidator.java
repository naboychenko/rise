package ru.mentoring.rise.skills.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mentoring.rise.common.dto.SkillDto;

@Component
public class SkillValuesValidator implements Validator {

    private static final int MINIMAL_LEVEL_VALUE = 1;
    private static final int MAXIMAL_LEVEL_VALUE = 10;

    @Override
    public boolean supports(Class<?> clazz) {
        return SkillDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SkillDto dto = (SkillDto) target;

        Integer level = dto.getLevel();
        if (level == null) {
            errors.rejectValue("level", "level is required");
        } else {
            if (level < MINIMAL_LEVEL_VALUE) {
                errors.rejectValue("level", "level value couldn't be less than " + MINIMAL_LEVEL_VALUE);
            }

            if (level > MAXIMAL_LEVEL_VALUE) {
                errors.rejectValue("level", "level value couldn't be more than " + MAXIMAL_LEVEL_VALUE);
            }
        }
    }
}
