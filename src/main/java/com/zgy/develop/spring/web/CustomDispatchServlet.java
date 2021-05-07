package com.zgy.develop.spring.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * 请求总口分发servlet
 * @author zgy
 * @data 2021/4/26 19:29
 */

public class CustomDispatchServlet extends HttpServlet {

    private Class<?> clazz;
    private Properties properties;

    public CustomDispatchServlet(Class<?> clazz, Properties properties) {
        this.clazz = clazz;
        this.properties = properties;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
