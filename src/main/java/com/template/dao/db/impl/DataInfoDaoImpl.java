package com.template.dao.db.impl;

import com.template.dao.common.api.ICommonDao;
import com.template.dao.db.api.IDataInfoDao;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name:ssh-code-generator
 * File Name:DataInfoDaoImpl
 * Package Name:com.template.dao.db.impl
 * Date:2019/4/26
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


@Repository("dataInfoDao")
public class DataInfoDaoImpl implements IDataInfoDao {

    private static final Logger logger = LoggerFactory.getLogger(DataInfoDaoImpl.class);

    @Resource(name = "commonDao")
    private ICommonDao commonDao;

    @Override
    public List<Map<String, Object>> queryTablesInfo(String tableName) {
        String hql = "select column_name as columnName, data_type as dataType, column_comment as columnComment, column_key as columnKey, extra " +
                "from information_schema.columns " +
                "where table_name like  '%" + tableName + "%' and table_schema = (select database()) order by ordinal_position";
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            Session session = this.commonDao.getSession();
            System.out.println(hql);
            Query query = session.createSQLQuery(hql);
            List list = query.list();
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                Object[] values = (Object[]) o;
                Map<String, Object> map = new HashMap<>();
                map.put("columnName", values[0].toString());
                map.put("dataType", values[1].toString());
                map.put("columnComment", values[2].toString());
                map.put("columnKey", values[3].toString());
                map.put("extra", values[4].toString());
                result.add(map);
            }
        } catch (Exception e) {
            logger.info("查询失败:" + e.getMessage());
        }
        return result;
    }
}
