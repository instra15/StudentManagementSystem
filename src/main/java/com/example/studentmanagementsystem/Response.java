package com.example.studentmanagementsystem;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response<T> {
    private T data;

    private boolean success;

    private String errorMsg;

    public static <K> Response<K> success(K data)
    {
        Response<K> response=new Response<>();
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

    public static <K> Response<K> fail(K data,Exception e)
    {
        Response<K> response=new Response<>();
        response.setData(data);
        response.setSuccess(false);
        response.setErrorMsg(e.getMessage());
        return response;
    }

    public static <K> Response<K> fail(Exception e)
    {
        Response<K> response=new Response<>();
        response.setSuccess(false);
        response.setErrorMsg(e.getMessage());
        return response;
    }

}
