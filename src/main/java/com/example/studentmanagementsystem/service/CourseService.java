package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.DTO.CourseDTO;
import com.example.studentmanagementsystem.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    Response<Page<CourseDTO>> searchAllCourse(Pageable pageable);
}
