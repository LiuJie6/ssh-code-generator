package com.template.service.utils;

import com.template.model.api.db.ColumnModel;
import com.template.model.api.db.TableModel;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Project Name:ssh-code-generator
 * File Name:GenerateUtils
 * Package Name:com.template.service.utils
 * Date:2019/4/28
 * Author:liujie
 * Description:用于代码生成的工具类
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


public class GenerateUtils {

    private static final String configFileName = "generator.properties";

    private static final Logger logger = LoggerFactory.getLogger(GenerateUtils.class);

    /**
     * 获取模板
     *
     * @return 模板
     */
    private static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add("template/Entity.java.vm");
        templates.add("template/Dao.java.vm");
        return templates;
    }

    /**
     * 获取配置文件
     *
     * @return 配置文件
     * @throws Exception 异常信息
     */
    private static Configuration getConfig() throws Exception {
        try {
            return new PropertiesConfiguration(configFileName);
        } catch (Exception e) {
            throw new Exception("获取配置文件失败：" + e.getMessage());
        }
    }

    /**
     * 表名转换成类名
     *
     * @param tableName   表名
     * @param tablePrefix 表名前缀
     * @return 类名
     */
    private static String tableNameToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tableName)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return nameToJava(tableName);
    }

    /**
     * 将表名（类名）或列名转换成符合java规则的
     *
     * @param name 名字
     * @return 转换结果
     */
    private static String nameToJava(String name) {
        //通过指定的符号将所有分隔的字母的首字母大写并去掉“_”
        return WordUtils.capitalizeFully(name, new char[]{'_'}).replace("_", "");
    }

    /**
     * 初始化Velocity引擎
     * --VelocityEngine是单例模式，线程安全
     * ENCODING_DEFAULT = UTF-8
     * OUTPUT_ENCODING = UTF-8
     */
    private static void velocityInit() {
        Properties prop = new Properties();
        //加载classpath目录下的vm文件
        prop.setProperty("file.resource.loader.class"
                , "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");

        Velocity.init(prop);
    }

    /**
     * 构造文件名
     *
     * @param template    模板
     * @param className   类名
     * @param packageName 包名
     * @return 文件名
     */
    private static String buildFileName(String template, String className, String packageName) {
        String fileName = "";
        String packagePath = "";
        if (StringUtils.isNotBlank(packageName)) {
            packagePath = packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Dao.java.vm")) {
            fileName = packagePath + "dao" + File.separator + className + "Dao.java";
        }
        if (template.contains("Entity.java.vm")) {
            fileName = packagePath + "entity" + File.separator + className + "Entity.java";
        }
        if (template.contains("Service.java.vm")) {
            fileName = packagePath + "service" + File.separator + className + "Service.java";
        }
        if (template.contains("ServiceImpl.java.vm")) {
            fileName = packagePath + "serviceImpl" + File.separator + className + "ServiceImpl.java";
        }

        return fileName;
    }


    public static void generateCode(Map<String, Object> table, List<Map<String, Object>> columns, ZipOutputStream zip)
            throws Exception {
        //配置信息
        Configuration configuration = getConfig();

        //配置表信息
        TableModel tableModel = new TableModel();
        tableModel.setTableName(table.get("tableName").toString());
        String className = tableNameToJava(tableModel.getTableName(), configuration.getString("tablePrefix"));
        tableModel.setClassName(className);
        tableModel.setClassname(StringUtils.uncapitalize(className));

        //配置列信息
        List<ColumnModel> columnModels = new ArrayList<>();
        for (Map<String, Object> column : columns) {
            ColumnModel columnModel = new ColumnModel();
            columnModel.setColumnName(column.get("columnName").toString());
            columnModel.setDataType(column.get("dataType").toString());
            columnModel.setComments(column.get("columnComment").toString());
            columnModel.setExtra(column.get("extra").toString());

            //列名转换成java属性名
            String attrName = nameToJava(columnModel.getColumnName());
            columnModel.setAttrName(attrName);
            columnModel.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型转换成java数据类型
            String attrType = configuration.getString(columnModel.getDataType(), "unknownType");
            columnModel.setAttrType(attrType);

            //该列是否是主键
            if ("PRI".equals(column.get("columnKey")) && tableModel.getPk() == null) {
                tableModel.setPk(columnModel);
            }

            columnModels.add(columnModel);
        }
        tableModel.setColumns(columnModels);

        //如果没有指明主键，就将第一个字段设置为主键
        if (tableModel.getPk() == null) {
            tableModel.setPk(tableModel.getColumns().get(0));
        }

        //初始化Velocity引擎
        velocityInit();

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableModel.getTableName());
        map.put("comments", tableModel.getComments());
        map.put("pk", tableModel.getPk());
        map.put("className", tableModel.getClassName());
        map.put("classname", tableModel.getClassname());
        map.put("pathName", tableModel.getClassname().toLowerCase());
        map.put("columns", tableModel.getColumns());
        map.put("package", configuration.getString("package"));
        map.put("author", configuration.getString("author"));
        map.put("email", configuration.getString("email"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for (int i = 0; i < templates.size(); i++) {
            String template = templates.get(i);

            //渲染模板
            StringWriter sw = new StringWriter();
            Template temp = Velocity.getTemplate(template, "UTF-8");
            temp.merge(context, sw);

            try {
                //添加到zip
                String fileName = buildFileName(template, tableModel.getClassName(), configuration.getString("package"));
                System.out.println("文件名-----" + fileName);
                zip.putNextEntry(new ZipEntry(fileName));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (Exception e) {
                throw new Exception("渲染模板失败，表名：" + tableModel.getTableName(), e);
            }

        }

    }


}
