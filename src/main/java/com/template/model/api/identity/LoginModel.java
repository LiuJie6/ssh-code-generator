package com.template.model.api.identity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Project Name:SSHTemplate
 * File Name:LoginModel
 * Package Name:com.template.model.api.identity
 * Date:2019/2/19
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */

@ApiModel(description = "登录数据模型")
public class LoginModel {

    @ApiModelProperty(dataType = "string", name = "username", required = true, value = "账户名称")
    private String username;

    @ApiModelProperty(dataType = "string", name = "password", required = true, value = "密码")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
