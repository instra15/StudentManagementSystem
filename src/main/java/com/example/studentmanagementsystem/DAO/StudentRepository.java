package com.example.studentmanagementsystem.DAO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByName(String name);

    Student findByStudentNo(String studentNo);

    List<Student> findByStudentNoIn(List<String> studentNos);

    Page<Student> findByNameContaining(String keyword, Pageable pageable);
}
