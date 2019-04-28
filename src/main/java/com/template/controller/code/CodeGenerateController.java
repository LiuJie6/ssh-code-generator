package com.template.controller.code;

import com.template.service.db.api.IDataInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Project Name:ssh-code-generator
 * File Name:CodeGenerateController
 * Package Name:com.template.controller.code
 * Date:2019/4/26
 * Author:liujie
 * Description:
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */


@RestController("codeGenerateController")
@RequestMapping("codeGenerate")
public class CodeGenerateController {

    private static final Logger logger = LoggerFactory.getLogger(CodeGenerateController.class);

    @Resource(name = "dataInfoService")
    private IDataInfoService dataInfoService;

    @RequestMapping("/query/tablesInfo")
    public List<Map<String,Object>> queryTablesInfo(@RequestParam(value = "tableName",defaultValue = "") String tableName)
            throws Exception{

        return this.dataInfoService.queryTablesInfo(tableName);
    }
}
