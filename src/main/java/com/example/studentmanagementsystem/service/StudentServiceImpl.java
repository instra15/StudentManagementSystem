package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.Converter.StudentConverter;
import com.example.studentmanagementsystem.DAO.Student;
import com.example.studentmanagementsystem.DAO.StudentRepository;
import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.Response;
import com.example.studentmanagementsystem.exception.StudentAlreadyExist;
import com.example.studentmanagementsystem.exception.StudentNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    public Response<StudentDTO> getStudentById(Long id)
    {
        Student student=studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException("Student not found with id: "+id));
        log.info("Search student by id: {}", id);
        return Response.success(StudentConverter.Convert(student));
    }

    public Response<StudentDTO> getStudentByName(String name)
    {
        Student student=studentRepository.findByName(name);
        if(student==null)
        {
            throw new StudentNotFoundException("Student not found with name: " + name);
        }
        log.info("Search student by name: {}",name);
        return Response.success(StudentConverter.Convert(student));
    }

    public Response<StudentDTO> getStudentByStudentNo(String studentNo)
    {
        Student student=studentRepository.findByStudentNo(studentNo);
        if(student==null)
        {
            throw new StudentNotFoundException("Student not found with no: " + studentNo);
        }
        log.info("Search student by student no: {}",studentNo);
        return Response.success(StudentConverter.Convert(student));
    }

    public Response<StudentDTO> addNewStudent(StudentDTO studentDTO)
    {
        Student student=StudentConverter.Convert(studentDTO);
        Student student1=studentRepository.findByStudentNo(student.getStudentNo());
        if(student1!=null)
        {
            throw new StudentAlreadyExist("Student: " + student.getStudentNo() + " : " + student.getName() + " exists.");
        }
        Student student2=studentRepository.save(student);
        log.info("Add student:studentno:{} name:{} age:{} classname:{}",studentDTO.getStudentNo(),studentDTO.getName(),studentDTO.getAge(),studentDTO.getClassName());
        return Response.success(StudentConverter.Convert(student2));
    }

    public Response<Map<String,Long>> deleteStudent(long id)
    {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("id:" + id + "does not exist."));
        Map<String,Long> result=new HashMap<>();
        result.put("Delete student by id:",id);
        studentRepository.delete(student);
        log.info("Delete student by id:{}",id);
        return Response.success(result);
    }

    /**
     * 当前 foundId.contains(id) 是 O(n) 操作，将 foundId 转为 Set 可将查找降到 O(1)。
     *
     */
    @Transactional
    public Response<Map<String,List<Long>>> deleteStudentByList(List<Long> idList)
    {
        Map<String,List<Long>> result=new HashMap<>();
        List<Student> foundStudent=studentRepository.findAllById(idList);
        List<Long> foundId=foundStudent.stream().map(Student::getId).toList();
        List<Long> unfoundId=new ArrayList<>();
        for (Long id : idList)
        {
            if (!foundId.contains(id))
            {
                unfoundId.add(id);
            }
        }

        studentRepository.deleteAllByIdInBatch(foundId);


        result.put("These id has been deleted:",foundId);
        result.put("These id can not been found:",unfoundId);
        return Response.success(result);
    }

    @Transactional
    public Response<StudentDTO> updateStudent(long id,StudentDTO studentDTO)
    {
        Student student=studentRepository.findById(id).orElseThrow(()->new StudentNotFoundException("id: " + id + " does not exist."));
        if (studentDTO.getName()!=null)     student.setName(studentDTO.getName());
        if (studentDTO.getAge()!=null)   student.setAge(studentDTO.getAge());
        if (studentDTO.getClassName()!=null)   student.setClassName(studentDTO.getClassName());
        log.info("Update student.");
        return Response.success(StudentConverter.Convert(student));
    }

    public Response<Page<StudentDTO>> searchAllStudent(Pageable pageable)
    {
        Page<Student> page=studentRepository.findAll(pageable);
        Page<StudentDTO> page1=page.map(StudentConverter::Convert);
        return Response.success(page1);
    }


}
