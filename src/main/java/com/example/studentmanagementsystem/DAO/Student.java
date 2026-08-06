package com.example.studentmanagementsystem.DAO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "学号")
    @Column(name = "`studentNO`")
    private String studentNo;

    @Schema(description = "姓名")
    @Column(name = "name")
    private String name;

    @Schema(description = "年龄")
    @Column(name = "age")
    private Integer age;

    @Schema(description = "班级名")
    @Column(name = "`className`")
    private String className;



}
