package com.servlet.example.servlet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author YZG
 */
@RestController
public class Demo {

    @RequestMapping(value = "demo")
    public void demo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Holle World</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>大家好,我是Servlet2</h1>");
        writer.println("</html>");
        writer.println("</body>");
    }

    @RequestMapping(path = {"${wechat.mp.authorize-code-path:/authorize_code}"})
    public String test(@Value("${request.path}") String path){
        return "product/demo";
    }
}
