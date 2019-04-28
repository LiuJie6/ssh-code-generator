package com.template.service.db.impl;

import com.template.dao.db.api.IDataInfoDao;
import com.template.model.api.db.CodeGenerateModel;
import com.template.service.db.api.IDataInfoService;
import com.template.service.utils.GenerateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

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
    public Map<String, Object> queryTableInfo(String tableName) {
        return this.dataInfoDao.queryTableInfo(tableName);
    }

    /**
     * 根据表名查询表中各列的信息
     *
     * @param tableName 表名
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> queryColumnsInfo(String tableName) {

        return this.dataInfoDao.queryColumnsInfo(tableName);
    }

    /**
     * 代码生成
     *
     * @param params   表名列表
     */
    @Override
    public byte[] codeGenerate(CodeGenerateModel params) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        String[] tableNames = params.getTableNames();
        for (int i = 0; i < tableNames.length; i++) {
            //查询表信息
            Map<String, Object> table = this.queryTableInfo(tableNames[i]);
            //查询列信息
            List<Map<String, Object>> columns = this.queryColumnsInfo(tableNames[i]);

            //生成代码
            GenerateUtils.generateCode(table, columns, zip);

        }
        zip.closeEntry();

        return outputStream.toByteArray();
    }
}
