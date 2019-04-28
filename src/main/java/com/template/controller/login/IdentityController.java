package com.template.controller.login;

import com.template.dao.basic.user.api.IUserDao;
import com.template.model.api.identity.LoginModel;
import com.template.model.basic.user.UserModel;
import com.template.model.response.ResponseResult;
import com.template.model.state.State;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Project Name:SSHTemplate
 * File Name:IdentityController
 * Package Name:com.template.controller.login
 * Date:2019/2/19
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


@RestController("identityController")
@RequestMapping("api/auth")
public class IdentityController {

    private final static Logger logger = LoggerFactory.getLogger(IdentityController.class);

    @Resource(name = "userDao")
    private IUserDao userDao;

    @PostMapping("login")
    @ApiOperation(value = "账户登录"
            , notes = "账户登录接口说明"
            , consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams(@ApiImplicitParam(dataTypeClass = LoginModel.class))
    @ApiResponses({@ApiResponse(response = ResponseResult.class, code = 200, message = "账户登录结果")})
    public Object login(@RequestBody LoginModel params) throws Exception{
        ResponseResult responseResult;
        UserModel userModel = new UserModel();
        userModel.setUsername(params.getUsername());
        userModel.setPassword(params.getPassword());
        userModel = this.userDao.queryModel(userModel);
        if(userModel == null){
            responseResult = new ResponseResult(false, State.FAILURE.getCode(),"用户名或密码错误");
        }else{
            responseResult = new ResponseResult(true,State.SUCCESS.getCode(),"");
        }
        return responseResult;
    }
}
