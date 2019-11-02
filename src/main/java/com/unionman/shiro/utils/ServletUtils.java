package com.unionman.shiro.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description Servlet工具类
 * @author Rong.Jia
 * @date 2019/03/06 9:56
 */
public class ServletUtils {

    /**
     * @description: 获取request 对象
     * @date 2019/04/16 08:59:22
     * @return HttpServletRequest request 对象
     */
    public static HttpServletRequest getHttpRequest(){

        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    }

}
