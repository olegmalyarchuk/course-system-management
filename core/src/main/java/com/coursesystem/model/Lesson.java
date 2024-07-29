package com.coursesystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "lesson", schema = "management")
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lesson extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "lesson_number", nullable = false)
    private Short lessonNumber;

    @NotNull
    @Column(name = "mark")
    @Min(value = 0)
    @Max(value = 10)
    private Short mark;

    @OneToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Set<Homework> homeworks = new HashSet<>();

}
