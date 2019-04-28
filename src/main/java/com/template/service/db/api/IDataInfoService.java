package com.template.service.db.api;

import com.template.model.api.db.CodeGenerateModel;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Project Name:ssh-code-generator
 * File Name:IDataInfoService
 * Package Name:com.template.service.db.api
 * Date:2019/4/26
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


public interface IDataInfoService {

    /**
     * 根据表名查询表信息
     *
     * @param tableName 表名
     * @return 查询结果
     */
    Map<String, Object> queryTableInfo(String tableName);

    /**
     * 根据表名查询表中各列的信息
     *
     * @param tableName 表名
     * @return 查询结果
     */
    List<Map<String, Object>> queryColumnsInfo(String tableName);

    /**
     * 代码生成
     *
     * @param params 表名列表
     */
    byte[] codeGenerate(CodeGenerateModel params) throws Exception;
}
