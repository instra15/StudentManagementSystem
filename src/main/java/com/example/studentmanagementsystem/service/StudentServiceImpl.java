package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.Converter.StudentConverter;
import com.example.studentmanagementsystem.DAO.Student;
import com.example.studentmanagementsystem.DAO.StudentRepository;
import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.Response;
import com.example.studentmanagementsystem.exception.StudentAlreadyExist;
import com.example.studentmanagementsystem.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    public Response<StudentDTO> getStudentById(Long id)
    {
        try
        {
            Student student=studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException("Student not found with id: "+id));
            return Response.isSuccess(StudentConverter.Convert(student),null);
        }
        catch (StudentNotFoundException e)
        {
            return Response.isSuccess(null,e);
        }
    }

    public Response<StudentDTO> getStudentByName(String name)
    {
        try
        {
            Student student=studentRepository.findByName(name);
            if(student==null)
            {
                throw new StudentNotFoundException("Student not found with name: " + name);
            }
            return Response.isSuccess(StudentConverter.Convert(student),null);
        }
        catch (StudentNotFoundException e)
        {
            return Response.isSuccess(null,e);
        }
    }

    public Response<StudentDTO> getStudentByStudentNo(String studentNo)
    {
        try
        {
            Student student=studentRepository.findByStudentNo(studentNo);
            if(student==null)
            {
                throw new StudentNotFoundException("Student not found with no: " + studentNo);
            }
            return Response.isSuccess(StudentConverter.Convert(student),null);
        }
        catch (StudentNotFoundException e)
        {
            return Response.isSuccess(null,e);
        }
    }

    public Response<StudentDTO> addNewStudent(StudentDTO studentDTO)
    {
        Student student=StudentConverter.Convert(studentDTO);
        Student student1=studentRepository.findByStudentNo(student.getStudentNo());
        if(student1!=null)
        {
            return Response.isSuccess(null,new StudentAlreadyExist("Student: " + student.getStudentNo() + ":" + student.getName() + "exists."));
        }
        Student student2=studentRepository.save(student);
        return Response.isSuccess(StudentConverter.Convert(student2),null);
    }

    public Response<StudentDTO> deleteStudent(long id)
    {
        try
        {
            Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("id:" + id + "does not exist."));
            studentRepository.delete(student);
            return Response.isSuccess(null,null);
        }
        catch (StudentNotFoundException e)
        {
            return Response.isSuccess(null,e);
        }
    }


}
