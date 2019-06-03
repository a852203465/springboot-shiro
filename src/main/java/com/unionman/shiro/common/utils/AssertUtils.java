package com.unionman.shiro.common.utils;

import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.SpringbootShiroException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @description: 判断工具类
 * @date 2019/01/15 09:18
 * @author Rong.Jia
 */
@Slf4j
public class AssertUtils {


    public static void isNull(Object obj, ResponseEnum responseEnum){
        if(null == obj){
            throw new SpringbootShiroException(responseEnum);
        }
    }

    public static void isNotNull(Object obj, ResponseEnum responseEnum){
        if(null != obj){
            throw new SpringbootShiroException(responseEnum);
        }
    }

    public static void isNull(String str, ResponseEnum responseEnum){
        if(StringUtils.isBlank(str)){
            throw new SpringbootShiroException(responseEnum);
        }
    }


    public static void isEqual(Object obj1, Object obj2, ResponseEnum responseEnum){

        if(obj1.equals(obj2)){
            throw new SpringbootShiroException(responseEnum);
        }
    }

    public static void isEmptyList(List<?> list, ResponseEnum responseEnum) {
        if (list.size() == 0) {
            throw new SpringbootShiroException(responseEnum);
        }
    }

    public static void isNotNullList(List<?> list, ResponseEnum responseEnum) {
        if (list != null && !list.isEmpty()) {
            throw new SpringbootShiroException(responseEnum);
        }
    }

    public static void isEmptyMap(Map<?, ?> map, ResponseEnum responseEnum) {
        if (map.size() == 0) {
            throw new SpringbootShiroException(responseEnum);
        }
    }

    public static void isBoolean(Boolean flag, ResponseEnum responseEnum){

        if(flag){
            throw new SpringbootShiroException(responseEnum);
        }

    }

    /**
     * @description: 判断obj 是否为空
     * @param obj 判断值
     * @return boolean
     */
    public static boolean isNull (Object obj) {

        if (obj == null) {
            return true;
        }

        if (obj instanceof String) {
            if (StringUtils.isBlank(obj.toString())) {
                return true;
            }
        }

        if (obj instanceof Optional) {
            return !((Optional) obj).isPresent();
        }

        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return CollectionUtils.isEmpty((Collection<?>) obj);
        }
        if (obj instanceof Map) {
            return MapUtils.isEmpty((Map)obj);
        }

        return false;

    }

    /**
     * @description: 判断obj 是否不为空
     * @param obj 判断值
     * @return boolean
     */
    public static boolean isNotNull (Object obj) {
        return !isNull(obj);
    }

    /**
     * @description: 判断obj 全部不为空
     * @param values 判断值
     * @return boolean
     */
    public static boolean allNotNull (final Object... values) {
        if (values == null) {
            return false;
        }

        for (final Object val : values) {
            if (val == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * @description: 判断第一个不为空， 第二个为空
     * @return
     */
    public static boolean isFirstNotNullAndSecondNull(Object obj, Object obj2) {

        if (isNotNull(obj) && isNull(obj2)) {
            return true;
        }

        return false;

    }

    /**
     * @description: 判断 是否 小于0
     * @return
     */
    public static boolean isNumberLess0(Integer int1) {

        if (isNull(int1)) {
            return false;
        }

        if (NumberUtils.compare(int1, 0) == -1) {
            return true;
        }

        return false;

    }

    /**
     * @description: 判断 是否 大于等于0
     * @return
     */
    public static boolean isNumberGreater0(Integer int1) {
        return !isNumberLess0(int1);

    }

    public static boolean isEquals(Object obj, Object obj1) {

        if (obj instanceof Number && obj1 instanceof Number) {
            return obj.equals(obj1);
        }

        if (obj instanceof String && obj1 instanceof String) {

            return StringUtils.equals(obj1.toString(), obj.toString());

        }

        return false;

    }








}
