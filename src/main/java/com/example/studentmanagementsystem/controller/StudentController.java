package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.DTO.group.AddGroup;
import com.example.studentmanagementsystem.DTO.group.UpdateGroup;
import com.example.studentmanagementsystem.Response;
import com.example.studentmanagementsystem.service.StudentService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     *  ‘@PathVariable’ 从url提取参数
     */
    @GetMapping("/student/search/id/{id}")
    public Response<StudentDTO> SearchStudentById(@PathVariable long id)
    {
        return studentService.getStudentById(id);
    }

    @GetMapping("/student/search/name/{name}")
    public Response<StudentDTO> SearchStudentByName(@PathVariable String name)
    {
        return studentService.getStudentByName(name);
    }

    @GetMapping("/student/search/no/{no}")
    public Response<StudentDTO> SearchStudentByNo(@PathVariable String no)
    {
        return studentService.getStudentByStudentNo(no);
    }

    /**
     *  ‘@PageableDefault’ 从url读取分页逻辑
     */
    @GetMapping("/student/search/page")
    public Response<Page<StudentDTO>> SearchAllStudent(@PageableDefault Pageable pageable)
    {
        return studentService.searchAllStudent(pageable);
    }

    @GetMapping("/student/search/page/keyword={keyword}")
    public Page<StudentDTO> SearchAllStudentContaining(@PathVariable String keyword, @PageableDefault Pageable pageable)
    {
        return studentService.getAllStudentContaining(keyword,pageable);
    }

    /**
     * '@RequestBody' 从前端接受json对象
     */
    @PostMapping("/student/add")
    public Response<StudentDTO> addNewStudent(@Validated(AddGroup.class) @RequestBody StudentDTO studentDTO)
    {
        return studentService.addNewStudent(studentDTO);
    }

    @DeleteMapping("/student/delete/id/{id}")
    public Response<Map<String,Long>> deleteStudent(@PathVariable @Min(1) long id)
    {
        return studentService.deleteStudent(id);
    }

    @DeleteMapping("/student/delete/list")
    public Response<Map<String,List<Long>>> deleteStudentByList(@RequestBody @NotEmpty List<Long> idList)
    {
        return studentService.deleteStudentByList(idList);
    }

    @PutMapping("/student/update/id/{id}")
    public Response<StudentDTO> updateStudent(@PathVariable @Min(1) long id,@Validated(UpdateGroup.class) @RequestBody StudentDTO studentDTO)
    {
        return studentService.updateStudent(id,studentDTO);
    }






}
