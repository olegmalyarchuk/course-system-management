package com.coursesystem.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "homework", schema = "management")
@EqualsAndHashCode(of = "id", callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Homework extends AuditModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "original_file_name", length = 500)
    private String originalFileName;

    @NotNull
    @Column(name = "stored_file_name", length = 500)
    private String storedFileName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Lesson lesson;
}
