package com.template.service.basic.user.impl;

import com.template.dao.basic.user.api.IUserDao;
import com.template.model.basic.user.UserModel;
import com.template.service.basic.user.api.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Project Name:SSHTemplate
 * File Name:UserServiceImpl
 * Package Name:com.template.service.basic.user.impl
 * Date:2019/2/18
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource(name = "userDao")
    private IUserDao userDao;

    /**
     * 添加一个user
     *
     * @param name     姓名
     * @param age      年龄
     * @param sex      性别
     * @param password 密码
     */
    @Override
    public void addUser(String name, int age, String sex, String password) throws Exception {
        UserModel userModel = new UserModel();
        if (age < 0) {
            throw new Exception("年龄错误");
        }
        if (!"female".equals(sex) && !"male".equals(sex)) {
            throw new Exception("性别只能为female或者male");
        }
        userModel.setUsername(name);
        userModel.setAge(age);
        userModel.setSex(sex);
        userModel.setPassword(password);
        this.userDao.addUser(userModel);
    }

    /**
     * 通过name查询user
     *
     * @param name     姓名
     * @param sex      性别
     * @param password 密码
     * @return 查询结果
     */
    @Override
    public UserModel queryModel(String name, String sex, String password) throws Exception {
        UserModel userModel = new UserModel();
        userModel.setUsername(name);
        userModel.setSex(sex);
        userModel.setPassword(password);
        return this.userDao.queryModel(userModel);
    }
}
