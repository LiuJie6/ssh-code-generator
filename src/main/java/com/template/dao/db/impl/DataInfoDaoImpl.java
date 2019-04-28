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


    /**
     * 根据表名查询表信息
     *
     * @param tableName 表名
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryTableInfo(String tableName) {
        Map<String, Object> map = new HashMap<>();
        String hql = "select table_name as tableName,engine,table_comment as tableComment,create_time as createTime " +
                "from information_schema.tables " +
                "where table_name = '" + tableName + "' and table_schema = (select database()) ";
        try {
            Session session = this.commonDao.getSession();
            Query query = session.createSQLQuery(hql);
            List list = query.list();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Object o = list.get(i);
                    Object[] values = (Object[]) o;
                    map.put("tableName", values[0].toString());
                    map.put("engine", values[1].toString());
                    map.put("tableComment", values[2].toString());
                    map.put("createTime", values[3].toString());
                }
            }
        } catch (Exception e) {
            logger.info("查询表信息失败：" + e.getMessage());
        }
        return map;
    }

    /**
     * 根据表名查询表中列的信息
     *
     * @param tableName 表名
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> queryColumnsInfo(String tableName) {
        String hql = "select column_name as columnName, data_type as dataType, column_comment as columnComment, column_key as columnKey, extra " +
                "from information_schema.columns " +
                "where table_name =  '" + tableName + "' and table_schema = (select database()) order by ordinal_position";
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            Session session = this.commonDao.getSession();
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
