package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.Converter.CourseConverter;
import com.example.studentmanagementsystem.DAO.Course;
import com.example.studentmanagementsystem.DAO.CourseRepository;
import com.example.studentmanagementsystem.DTO.CourseDTO;
import com.example.studentmanagementsystem.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    public Response<Page<CourseDTO>> searchAllCourse(Pageable pageable)
    {
        Page<Course> page=courseRepository.findAll(pageable);
        return Response.success(page.map(CourseConverter::Convert));
    }




}
