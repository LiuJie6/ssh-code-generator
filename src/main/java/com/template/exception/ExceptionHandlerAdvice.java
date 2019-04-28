package com.template.exception;

import com.template.model.response.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Project Name:electronic-invoice
 * File Name:ExceptionHandlerAdvice
 * Package Name:com.yk.invoice.exception
 * Date:2018/12/19
 * Author:liujie
 * Description:异常处理
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public Object exception(Exception e, WebRequest request){
        return new ResponseResult(false,"0002",e.getMessage());
    }


}
