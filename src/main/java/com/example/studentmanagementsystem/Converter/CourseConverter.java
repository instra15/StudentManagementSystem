package com.example.studentmanagementsystem.Converter;

import com.example.studentmanagementsystem.DAO.Course;
import com.example.studentmanagementsystem.DTO.CourseDTO;

public class CourseConverter {

    public static Course Convert(CourseDTO courseDTO)
    {
        Course s=new Course();
        s.setCourse_name(courseDTO.getCourse_name());
        s.setCredit(courseDTO.getCredit());
        s.setHours(courseDTO.getHours());
        s.setTeacher(courseDTO.getTeacher());
        s.setClassroom(courseDTO.getClassroom());
        s.setSchedule(courseDTO.getSchedule());
        s.setMax_students(courseDTO.getMax_students());
        s.setDescription(courseDTO.getDescription());
        return s;
    }

    public static CourseDTO Convert(Course course)
    {
        CourseDTO s=new CourseDTO();
        s.setCourse_name(course.getCourse_name());
        s.setCredit(course.getCredit());
        s.setHours(course.getHours());
        s.setTeacher(course.getTeacher());
        s.setClassroom(course.getClassroom());
        s.setSchedule(course.getSchedule());
        s.setMax_students(course.getMax_students());
        s.setDescription(course.getDescription());
        return s;
    }

}
