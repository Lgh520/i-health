package com.project.ihealth.po;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestResponse<T> {
    private Integer code;
    private T data;
    private String message;

    public static <T> RestResponse<T> success(T data){
        return RestResponse.<T>builder()
                .code(0)
                .message("成功")
                .data(data)
                .build();
    }

    public static <T> RestResponse<T> error(int code,String msg){
        return RestResponse.<T>builder()
                .code(code)
                .message(msg)
                .data(null)
                .build();
    }
}
