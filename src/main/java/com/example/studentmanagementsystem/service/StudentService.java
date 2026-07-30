package com.example.studentmanagementsystem.service;


import com.example.studentmanagementsystem.DAO.Student;
import com.example.studentmanagementsystem.Response;

public interface StudentService {
    Response<Student> getStudentById(Long id);

    Response<Student> getStudentByStudentNo(String studentNo);

    Response<Student> getStudentByName(String name);

    Response<Student> addNewStudent(Student student);

    Response<Student> deleteStudent(long id);


}
