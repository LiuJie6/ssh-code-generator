package com.template.controller.basic;

import com.template.model.basic.user.UserModel;
import com.template.model.response.ResponseResult;
import com.template.model.state.State;
import com.template.service.basic.user.api.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Project Name:SSHTemplate
 * File Name:UserController
 * Package Name:com.template.controller.basic
 * Date:2019/2/18
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


@RestController("userController")
@RequestMapping("api/user")
@CrossOrigin
public class UserController {

    @Resource(name = "userService")
    private IUserService userService;

    @Value("${basic.test}")
    private String testContent;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "addOne")
    //@RequestMapping(value = "addOne")
    public Object addUser(@RequestParam(value = "username") String name,
                          @RequestParam(value = "age",defaultValue = "1",required = false) int age,
                          @RequestParam(value = "sex",defaultValue = "male",required = false) String sex,
                          @RequestParam(value = "password") String password) throws Exception {
        this.userService.addUser(name, age, sex, password);
        return new ResponseResult(true, State.SUCCESS.getCode(), "");
    }

    @PostMapping(value = "query")
    //@RequestMapping(value = "query")
    public Object queryModel(@RequestParam(name = "username") String name,
                             @RequestParam(name = "sex", defaultValue = "male") String sex,
                             @RequestParam(name = "password") String password) throws Exception {
        logger.info(testContent);
        UserModel userModel = this.userService.queryModel(name, sex, password);
        Map<String, Object> map = new HashMap<>();
        map.put("UserModel", userModel);
        return new ResponseResult(true, State.SUCCESS.getCode(), "", map);
    }
}
