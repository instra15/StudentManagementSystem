package com.example.studentmanagementsystem.Converter;

import com.example.studentmanagementsystem.DAO.Student;
import com.example.studentmanagementsystem.DTO.StudentDTO;

public class StudentConverter {
    public static Student Convert(StudentDTO studentDTO)
    {
        Student s=new Student();
        s.setName(studentDTO.getName());
        s.setAge(studentDTO.getAge());
        s.setClassName(studentDTO.getClassName());
        s.setStudentNo(studentDTO.getStudentNo());
        return s;
    }

    public static StudentDTO Convert(Student student)
    {
        StudentDTO s=new StudentDTO();
        s.setName(student.getName());
        s.setAge(student.getAge());
        s.setClassName(student.getClassName());
        s.setStudentNo(student.getStudentNo());
        return s;
    }

}
