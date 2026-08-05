package com.example.studentmanagementsystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private String course_name;

    private Integer credit;

    private Integer hours;

    private String teacher;

    private String classroom;

    private String schedule;

    private Integer max_students;

    private String description;

}
