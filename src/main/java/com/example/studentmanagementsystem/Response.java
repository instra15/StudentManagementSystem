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

    public static <K> Response<K> isSuccess(K data,Exception e)
    {
        Response<K> response=new Response<>();
        if(e!=null)
        {
            response.setSuccess(false);
            response.setErrorMsg(e.getMessage());
            return response;
        }
        response.setData(data);
        response.setSuccess(true);
        return response;
    }

}
