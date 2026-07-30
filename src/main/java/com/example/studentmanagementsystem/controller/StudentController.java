package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.DAO.Student;
import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.Response;
import com.example.studentmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/find/id:{id}")
    public Response<StudentDTO> SearchStudentById(@PathVariable long id)
    {
        return studentService.getStudentById(id);
    }

    @GetMapping("/student/find/name:{name}")
    public Response<StudentDTO> SearchStudentByName(@PathVariable String name)
    {
        return studentService.getStudentByName(name);
    }

    @GetMapping("/student/find/no:{no}")
    public Response<StudentDTO> SearchStudentByNo(@PathVariable String no)
    {
        return studentService.getStudentByStudentNo(no);
    }

}
