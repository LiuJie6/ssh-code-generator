package com.template.service.db.impl;

import com.template.dao.db.api.IDataInfoDao;
import com.template.service.db.api.IDataInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Project Name:ssh-code-generator
 * File Name:DataInfoServiceImpl
 * Package Name:com.template.service.db.impl
 * Date:2019/4/26
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


@Service("dataInfoService")
public class DataInfoServiceImpl implements IDataInfoService {

    private static final Logger logger = LoggerFactory.getLogger(DataInfoServiceImpl.class);

    @Resource(name = "dataInfoDao")
    private IDataInfoDao dataInfoDao;


    /**
     * 根据表名查询表信息
     *
     * @param tableName 表名
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> queryTablesInfo(String tableName) {
        return this.dataInfoDao.queryTablesInfo(tableName);
    }

    /**
     * 代码生成
     *
     * @param tableNames 表名列表
     * @param response   response
     */
    @Override
    public void codeGenerate(String[] tableNames, HttpServletResponse response) {

    }
}
