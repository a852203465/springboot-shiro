package com.unionman.shiro.utils;

import com.unionman.shiro.constants.CommonConstant;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @description: Base64工具
 * @author Rong.Jia
 * @date 2018/8/30 15:49
 */
public class Base64ConvertUtils {

    /**
     * @description: 加密JDK1.8
     * @param str 待加密字符串
     * @author Rong.Jia
     * @date 2018/8/30 15:49
     * @return java.lang.String 加密后字符串
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes(CommonConstant.PROJECT_ENCODING_UTF8));
        return new String(encodeBytes);
    }

    /**
     * @description: 解密JDK1.8
     * @param str 待解密字符串
     * @author Rong.Jia
     * @date 2018/8/30 15:49
     * @return java.lang.String 解密后字符串
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes(CommonConstant.PROJECT_ENCODING_UTF8));
        return new String(decodeBytes);
    }

}
