package com.example.studentmanagementsystem.exception;

public class StudentAlreadyExist extends RuntimeException {
    public StudentAlreadyExist(String message) {
        super(message);
    }
}
