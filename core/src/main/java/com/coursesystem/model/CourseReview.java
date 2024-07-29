package com.coursesystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "course_review", schema = "management")
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString()
public class CourseReview extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @NotNull
    @Column(name = "review")
    private String review;
}
