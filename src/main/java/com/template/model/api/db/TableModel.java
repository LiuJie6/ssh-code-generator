package com.template.model.api.db;

import java.util.List;

/**
 * Project Name:ssh-code-generator
 * File Name:TableModel
 * Package Name:com.template.model.api.db
 * Date:2019/4/28
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */
public class TableModel {

    // 表的名称
    private String tableName;
    // 表的备注
    private String comments;
    // 表的主键
    private ColumnModel pk;
    // 表的列名(不包含主键)
    private List<ColumnModel> columns;
    // 类名(第一个字母大写)，如：sys_user => SysUser
    private String className;
    // 类名(第一个字母小写)，如：sys_user => sysUser
    private String classname;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnModel getPk() {
        return pk;
    }

    public void setPk(ColumnModel pk) {
        this.pk = pk;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnModel> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
}
