package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.DTO.CourseDTO;
import com.example.studentmanagementsystem.Response;
import com.example.studentmanagementsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/course/search/page")
    public Response<Page<CourseDTO>> searchAllCourse(@PageableDefault Pageable pageable)
    {
        return courseService.searchAllCourse(pageable);
    }
}
