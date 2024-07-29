package com.coursesystem.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("course")
@NoArgsConstructor
@AllArgsConstructor
public class CourseProperties {

    private Double passingGrade;
}

