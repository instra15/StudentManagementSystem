package com.example.studentmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication

//Page<StudentDTO> 会按照传统的 JSON 格式序列化，包含 content、totalElements、totalPages、number、size 等字段，前端可以正常解析。
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)

public class StudentManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementSystemApplication.class, args);
    }

}
