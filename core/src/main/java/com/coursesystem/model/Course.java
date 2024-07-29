package com.coursesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "course", schema = "management")
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"instructors", "students"})
public class Course extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "number_of_lessons")
    private Short numberOfLessons;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "course_instructors",
            schema = "management",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "instructors_id")}
    )
    @JsonIgnoreProperties(value = {"students", "instructors", "courses"}, allowSetters = true)
    private Set<Instructor> instructors = new HashSet<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "course_students",
            schema = "management",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "students_id")}
    )
    @JsonIgnoreProperties(value = {"students", "instructors", "courses"}, allowSetters = true)
    private Set<Student> students = new HashSet<>();
}


