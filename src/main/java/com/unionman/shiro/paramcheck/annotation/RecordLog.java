package com.unionman.shiro.paramcheck.annotation;

import java.lang.annotation.*;

/**
 * @author Zhifeng.Zeng
 * @description 记录日志注解
 * @date 2019/3/7
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface RecordLog {
    String value() default "";

    String operation() default "";
}
