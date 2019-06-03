package com.unionman.shiro.paramcheck.annotation;

import java.lang.annotation.*;

/**
 * @author Zhifeng.Zeng
 * @description 延长token时长注解
 * @date 2019/4/11
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface ExtendeAccessToken {
}
