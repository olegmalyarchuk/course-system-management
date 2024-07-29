package com.coursesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "instructor", schema = "management")
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"courses"})
public class Instructor extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true, updatable = false)
    private User user;

    @ManyToMany(mappedBy = "instructors")
    @JsonIgnoreProperties(value = {"students", "instructors", "courses"}, allowSetters = true)
    private Set<Course> courses;
}
