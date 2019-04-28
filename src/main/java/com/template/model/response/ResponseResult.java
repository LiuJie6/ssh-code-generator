package com.template.model.response;

import java.util.HashMap;
import java.util.Map;

/**
 * Project Name:electronic-invoice
 * File Name:ResponseResult
 * Package Name:com.yk.invoice.model.response
 * Date:2018/9/30
 * Author:liujie
 * Description:封装接口返回的数据
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */


public class ResponseResult {

    private boolean IsSuccess;   // 状态
    private String Code;         // 编码
    private String ErrorMessage; // 消息
    private Map<String, Object> data;           // 数据

    public ResponseResult(boolean isSuccess, String code, String errorMessage, Map<String, Object> data) {
        IsSuccess = isSuccess;
        Code = code;
        ErrorMessage = errorMessage;
        this.data = data;
    }

    public ResponseResult(boolean isSuccess, String code, String errorMessage) {
        IsSuccess = isSuccess;
        Code = code;
        ErrorMessage = errorMessage;
        this.data = new HashMap<>();
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public String getCode() {
        return Code;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
