package com.servlet.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 使用@WebServlet须在主入口上使用@ServletComponentScan
 * 不指定name的情况下，name默认值为类全路径，即com.servlet.webmvc.servlet.MyServlet
 * 在 SpringBootApplication 上使用@ServletComponentScan 注解后，Servlet、Filter、Listener
 *      可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册
 * @author YZG
 */
// @WebServlet(urlPatterns = "/jsp/servlet/*",description = "Servlet的说明")
public class MyServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">>>>>>>>>>>>>>doGet()<<<<<<<<<<<<<<<<<");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(">>>>>>>>>>>>>>>>>>>doPost()<<<<<<<<<<<<<<<<<<<<<<");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Holle World</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>大家好,我是Servlet2</h1>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
