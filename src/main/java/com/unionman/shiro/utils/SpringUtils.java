package com.unionman.shiro.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description: 获得Spring管理对象
 * @author Rong.Jia
 * @date 2019/04/15
 */
@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null){
            SpringUtils.applicationContext = applicationContext;
        }
    }


    /**
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * @description: 通过name获取 Bean.
     * @param name Bean名称
     * @date 2019/04/16 09:01:22
     * @return Object bean
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /**
     * @description: 通过class获取Bean.
     * @param clazz 对象的class 对象
     * @date 2019/04/16 09:01:22
     * @return T Bean
     */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @description: 通过name,以及Clazz返回指定的Bean
     * @param name  Bean名称
     * @param clazz 对象的class 对象
     * @date 2019/04/16 09:01:22
     * @return T Bean
     */
    public static <T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name,clazz);
    }
}
