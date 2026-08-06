package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.DTO.StudentDTO;
import com.example.studentmanagementsystem.DTO.group.AddGroup;
import com.example.studentmanagementsystem.DTO.group.UpdateGroup;
import com.example.studentmanagementsystem.Response;
import com.example.studentmanagementsystem.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "学生管理",description = "学生信息的增删改查接口")
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     *  ‘@PathVariable’ 从url提取参数
     */
    @Operation(summary = "根据ID查询学生")
    @GetMapping("/search/id/{id}")
    public Response<StudentDTO> SearchStudentById(@PathVariable long id)
    {
        return studentService.getStudentById(id);
    }

    @Operation(summary = "根据名字查询学生")
    @GetMapping("/search/name/{name}")
    public Response<StudentDTO> SearchStudentByName(@PathVariable String name)
    {
        return studentService.getStudentByName(name);
    }

    @Operation(summary = "根据学号查询学生")
    @GetMapping("/search/no/{no}")
    public Response<StudentDTO> SearchStudentByNo(@PathVariable String no)
    {
        return studentService.getStudentByStudentNo(no);
    }

    /**
     *  ‘@PageableDefault’ 从url读取分页逻辑
     */
    @Operation(summary = "分页查询学生")
    @GetMapping("/search/page")
    public Response<Page<StudentDTO>> SearchAllStudent(@PageableDefault Pageable pageable)
    {
        return studentService.searchAllStudent(pageable);
    }

    @Operation(summary = "模糊搜索",description = "学生姓名模糊搜索")
    @GetMapping("/search/page/keyword={keyword}")
    public Response<Page<StudentDTO>> SearchAllStudentContaining(@PathVariable String keyword, @PageableDefault Pageable pageable)
    {
        return studentService.getAllStudentContaining(keyword,pageable);
    }

    /**
     * '@RequestBody' 从前端接受json对象
     */
    @Operation(summary = "添加学生")
    @PostMapping("/add")
    public Response<StudentDTO> addNewStudent(@Validated(AddGroup.class) @RequestBody StudentDTO studentDTO)
    {
        return studentService.addNewStudent(studentDTO);
    }

    @Operation(summary = "批量添加学生")
    @PostMapping("/add/batch")
    public Response<Map<String,List<String>>> addBatchStudent(@NotEmpty @RequestBody List<@Valid StudentDTO> studentDTOS)
    {
        return studentService.addBatchStudent(studentDTOS);
    }

    @Operation(summary = "删除学生（id）")
    @DeleteMapping("/delete/id/{id}")
    public Response<Map<String,Long>> deleteStudent(@PathVariable @Min(1) long id)
    {
        return studentService.deleteStudent(id);
    }

    @Operation(summary = "批量删除学生")
    @DeleteMapping("/delete/list")
    public Response<Map<String,List<Long>>> deleteStudentByList(@RequestBody @NotEmpty List<Long> idList)
    {
        return studentService.deleteStudentByList(idList);
    }

    @Operation(summary = "更新学生信息")
    @PutMapping("/update/id/{id}")
    public Response<StudentDTO> updateStudent(@PathVariable @Min(1) long id,@Validated(UpdateGroup.class) @RequestBody StudentDTO studentDTO)
    {
        return studentService.updateStudent(id,studentDTO);
    }






}
