package com.example.studentmanagementsystem;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private T data;

    private boolean success;

    private String errorMsg;

    public static <K> Response<K> isSuccess(K data,Exception e)
    {
        if (e==null)
        {
            return new Response<>(data,true,null);
        }
        else
        {
            return new Response<>(data,false,e.getMessage());
        }
    }

}
