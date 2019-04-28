package com.template.dao.db.api;

import java.util.List;
import java.util.Map;

/**
 * Project Name:ssh-code-generator
 * File Name:IDataInfoDao
 * Package Name:com.template.dao.db.api
 * Date:2019/4/26
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


public interface IDataInfoDao {

    List<Map<String,Object>> queryTablesInfo(String tableName);
}
