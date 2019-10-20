package ru.mentoring.rise.skills.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skills")
@Document(collection = "skills")
@EntityListeners(AuditingEntityListener.class)
public class Skill implements Serializable {

    @Id
    @GeneratedValue(generator = "skills_generator")
    @SequenceGenerator(
        name = "skills_generator",
        sequenceName = "skills_sequence",
        initialValue = 1000
    )
    private Long id;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "skill_name")
    private String skillName;

    @Min(1)
    @Max(10)
    @Column(name = "skill_level")
    private int level;

    @Column(name = "skill_rank")
    private SkillRank rank;
}

