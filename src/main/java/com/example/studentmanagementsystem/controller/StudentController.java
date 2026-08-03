package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.DTO.group.AddGroup;
import com.example.studentmanagementsystem.DTO.group.UpdateGroup;
import com.example.studentmanagementsystem.Response;
import com.example.studentmanagementsystem.service.StudentService;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/student/find/id/{id}")
    public Response<StudentDTO> SearchStudentById(@PathVariable long id)
    {
        return studentService.getStudentById(id);
    }

    @GetMapping("/student/find/name/{name}")
    public Response<StudentDTO> SearchStudentByName(@PathVariable String name)
    {
        return studentService.getStudentByName(name);
    }

    @GetMapping("/student/find/no/{no}")
    public Response<StudentDTO> SearchStudentByNo(@PathVariable String no)
    {
        return studentService.getStudentByStudentNo(no);
    }

    @PostMapping("/student/add")
    public Response<StudentDTO> addNewStudent(@Validated(AddGroup.class) @RequestBody StudentDTO studentDTO)
    {
        return studentService.addNewStudent(studentDTO);
    }

    @DeleteMapping("/student/delete/id/{id}")
    public Response<StudentDTO> deleteStudent(@PathVariable @Min(1) long id)
    {
        return studentService.deleteStudent(id);
    }

    @PutMapping("/student/update/id/{id}")
    public Response<StudentDTO> updateStudent(@PathVariable @Min(1) long id,@Validated(UpdateGroup.class) @RequestBody StudentDTO studentDTO)
    {
        return studentService.updateStudent(id,studentDTO);
    }

}
