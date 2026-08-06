package com.example.studentmanagementsystem.service;


import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.Response;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Response<StudentDTO> getStudentById(Long id);

    Response<StudentDTO> getStudentByStudentNo(String studentNo);

    Response<StudentDTO> getStudentByName(String name);

    Response<StudentDTO> addNewStudent(StudentDTO studentDTO);

    Response<Map<String,Long>> deleteStudent(long id);

    Response<Map<String,List<Long>>> deleteStudentByList(List<Long> idList);

    Response<StudentDTO> updateStudent(long id,StudentDTO studentDTO);

    Response<Page<StudentDTO>> searchAllStudent(Pageable pageable);

    Response<Page<StudentDTO>> getAllStudentContaining(String keyword, Pageable pageable);

    Response<Map<String, List<String>>> addBatchStudent(@NotEmpty List<StudentDTO> studentDTOS);
}
