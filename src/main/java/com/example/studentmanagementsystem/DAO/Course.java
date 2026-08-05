package com.example.studentmanagementsystem.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`course_id`")
    private Long course_id;

    @Column(name = "`course_name`")
    private String course_name;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "hours")
    private Integer hours;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "classroom")
    private String classroom;

    @Column(name = "schedule")
    private String schedule;

    @Column(name = "`max_students`")
    private Integer max_students;

    @Column(name = "description")
    private String description;


}
