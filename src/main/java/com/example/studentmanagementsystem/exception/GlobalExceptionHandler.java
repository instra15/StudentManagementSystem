package com.example.studentmanagementsystem.exception;

import com.example.studentmanagementsystem.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<Void> handleStudentNotFound(StudentNotFoundException e)
    {
        log.error("Find student but fail:student does not exist.");
        return Response.fail(e);
    }

    @ExceptionHandler(StudentAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<Void> handleStudentAlreadyExistException(StudentAlreadyExist e)
    {
        log.error("Find student but fail:student has already existed.");
        return Response.fail(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error("Valid student object added.");
        return Response.fail(e);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handleBindException(BindException e)
    {
        log.error("Valid student object added .");
        return Response.fail(e);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Void> handlePageableException(InvalidDataAccessApiUsageException e)
    {
        log.error("Invalid pageable data.");
        return Response.fail(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> handleOtherException(Exception e)
    {
        log.error("Unknown error.");
        return Response.fail(e);
    }


}
