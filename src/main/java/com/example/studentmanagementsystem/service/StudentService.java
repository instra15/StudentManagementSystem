package com.example.studentmanagementsystem.service;


import com.example.studentmanagementsystem.DAO.Student;
import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.Response;

public interface StudentService {
    Response<StudentDTO> getStudentById(Long id);

    Response<StudentDTO> getStudentByStudentNo(String studentNo);

    Response<StudentDTO> getStudentByName(String name);

    Response<StudentDTO> addNewStudent(Student student);

    Response<StudentDTO> deleteStudent(long id);


}
