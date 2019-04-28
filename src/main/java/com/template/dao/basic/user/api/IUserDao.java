package com.template.dao.basic.user.api;

import com.template.model.basic.user.UserModel;

/**
 * Project Name:SSHTemplate
 * File Name:IUserDao
 * Package Name:com.template.dao.basic.user.api
 * Date:2019/2/18
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


public interface IUserDao {

    /**
     * 添加user
     *
     * @param userModel userModel
     * @throws Exception 异常信息
     */
    void addUser(UserModel userModel) throws Exception;

    /**
     * 查询user
     *
     * @param userModel 查询模型
     * @return 查询结果
     * @throws Exception 异常信息
     */
    UserModel queryModel(UserModel userModel) throws Exception;
}
