package com.template.controller.code;

import com.template.model.api.db.CodeGenerateModel;
import com.template.model.response.ResponseResult;
import com.template.model.state.State;
import com.template.service.db.api.IDataInfoService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    public Object queryTablesInfo(@RequestParam(value = "tableName", defaultValue = "") String tableName)
            throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("data", this.dataInfoService.queryTableInfo(tableName));
        return new ResponseResult(true, State.SUCCESS.getCode(), "", data);
    }

    /**
     * 生成代码
     *
     * @param params   参数
     * @param response 响应
     * @return 操作结果
     */
    @RequestMapping("code/generate")
    public Object generateCode(@RequestBody CodeGenerateModel params, HttpServletResponse response) throws Exception{
        byte[] data = this.dataInfoService.codeGenerate(params);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"framework.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
        return new ResponseResult(true, State.SUCCESS.getCode(), "");
    }
}
