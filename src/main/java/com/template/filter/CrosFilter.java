package com.template.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project Name:SSHTemplate
 * File Name:CrosFilter
 * Package Name:com.template.filter
 * Date:2019/2/18
 * Author:liujie
 * Description:实现跨域的Filter
 * Copyright (c) 2019, 重庆云凯科技有限公司 All Rights Reserved.
 */



public class CrosFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())){
            resp.setStatus(200);
            return;
        }
        filterChain.doFilter(servletRequest, resp);
    }

    @Override
    public void destroy() {
    }
}
