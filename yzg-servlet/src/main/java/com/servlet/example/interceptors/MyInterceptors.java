package com.servlet.example.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 * @author yangzhiguo  2017/10/24.
 */
public class MyInterceptors implements HandlerInterceptor {

    /**
     * 拦截前处理
     *
     * @param request request
     * @param response response
     * @param handler handler
     * @return boolean
     * @throws Exception exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        System.out.println("MyInterceptor在请求处理之前进行调用(controller方法调用之前)");
        // 只有返回true才会继续向下执行,返回false取消当前请求
        return true;
    }

    /**
     * 调用Controller之后视图渲染之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
        System.out.println("MyInterceptor在请求处理之后进行调用,但是在视图被渲染之前(controller方法调用之后)");
    }

    /**
     * 在视图渲染之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("MyInterceptorl在整个请求之后调用,也就是在DispatcherServlet渲染了对应的视图" +
                "之后执行(主要是用于进行资源清理工作)");
    }
}
