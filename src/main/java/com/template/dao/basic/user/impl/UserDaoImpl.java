package com.template.dao.basic.user.impl;

import com.template.dao.basic.user.api.IUserDao;
import com.template.dao.common.api.ICommonDao;
import com.template.model.basic.user.UserModel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashSet;

/**
 * Project Name:SSHTemplate
 * File Name:UserDaoImpl
 * Package Name:com.template.dao.basic.user.impl
 * Date:2019/2/18
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


@Repository("userDao")
public class UserDaoImpl implements IUserDao {

    @Resource(name = "commonDao")
    private ICommonDao commonDao;

    /**
     * 添加user
     *
     * @param userModel userModel
     * @throws Exception 异常信息
     */
    @Override
    public void addUser(UserModel userModel) throws Exception {
        this.commonDao.saveModel(userModel);
    }

    /**
     * 查询user
     *
     * @param userModel 查询模型
     * @return 查询结果
     * @throws Exception 异常信息
     */
    @Override
    public UserModel queryModel(UserModel userModel) throws Exception {
        return this.commonDao.queryModel(this.getCondition(userModel), UserModel.class);
    }

    /**
     * 拼接查询条件
     *
     * @param userModel 查询模型
     * @return 查询条件
     */
    private String getCondition(UserModel userModel) {
        HashSet<String> conditions = new HashSet<>();
        if (userModel.getUserId() > 0) {
            conditions.add("model.userId=" + userModel.getUserId());
        }
        if (userModel.getUsername() != null && !userModel.getUsername().isEmpty()) {
            conditions.add("model.username='" + userModel.getUsername() + "'");
        }
        if (userModel.getAge() > 0) {
            conditions.add("model.age=" + userModel.getAge());
        }
        if (userModel.getSex() != null && !userModel.getSex().isEmpty()) {
            conditions.add("model.sex='" + userModel.getSex() + "'");
        }
        if (userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
            conditions.add("model.password='" + userModel.getPassword() + "'");
        }
        return this.commonDao.getAndCondition(conditions);
    }
}
