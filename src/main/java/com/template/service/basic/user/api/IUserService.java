package com.template.service.basic.user.api;

import com.template.model.basic.user.UserModel;

/**
 * Project Name:SSHTemplate
 * File Name:IUserService
 * Package Name:com.template.service.basic.user.api
 * Date:2019/2/18
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */
public interface IUserService {

    /**
     * 添加一个user
     *
     * @param name     姓名
     * @param age      年龄
     * @param sex      性别
     * @param password 密码
     */
    void addUser(String name, int age, String sex, String password) throws Exception;

    /**
     * 查询user
     *
     * @param name     姓名
     * @param sex      性别
     * @param password 密码
     * @return 查询结果
     */
    UserModel queryModel(String name, String sex, String password) throws Exception;
}
