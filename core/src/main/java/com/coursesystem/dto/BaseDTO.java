package com.coursesystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO implements Serializable {
    private LocalDateTime updatedAt = LocalDateTime.now();
    private String updatedBy;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String createdBy;
}
