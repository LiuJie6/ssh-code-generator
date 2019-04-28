package com.template.model.state;

/**
 * Project Name:electronic-invoice
 * File Name:State
 * Package Name:com.yk.invoice.model.state
 * Date:2018/10/8
 * Author:zhangju
 * Description:
 * Copyright (c) 2018, 重庆云凯科技有限公司 All Rights Reserved.
 */


public enum State {
    SUCCESS("0000"), FAILURE("0001"), EXCEPTION("0002");

    private static final String SUCCESS_CODE = "0000";
    private static final String FAILURE_CODE = "0001";
    private static final String EXCEPTION_CODE = "0002";

    private String code;

    State(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static State parse(String code) {
        switch (code) {
            case SUCCESS_CODE:
                return SUCCESS;
            case FAILURE_CODE:
                return FAILURE;
            case EXCEPTION_CODE:
                return EXCEPTION;
            default:
                return FAILURE;
        }
    }
}
