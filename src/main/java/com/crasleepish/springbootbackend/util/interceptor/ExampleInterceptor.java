package com.crasleepish.springbootbackend.util.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器需要在配置因中用addInterceptors方法注册，见OverallConfig类
 */
public class ExampleInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ExampleInterceptor.class);

    /**
     * 拦截之前调用（进入Controller之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Interceptor set off: preHandle");
        return true;
    }

    /**
     * 调用Controller之后，视图渲染之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Interceptor set off: postHandle");
    }

    /**
     * 完成拦截之后，用于清理资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("Interceptor set off: afterCompletion");
    }
}
