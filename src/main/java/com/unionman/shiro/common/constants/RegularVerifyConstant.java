package com.unionman.shiro.common.constants;

/**
 *
 * @description: 正则校验常数
 * @author: Rong.Jia
 * @date: 2019/02/27 10:13:22
 */
public class RegularVerifyConstant {

    /**
     * @description: 长度指定提示信息后缀
     * @author: Rong.Jia
     * @date 2019/02/27 10:13:22
     */
    public  static  final String  LENGTH_DISCREPANCY = "长度不符";

    /**
     * @description: 格式指定提示信息后缀
     * @author: Rong.Jia
     * @date 2019/02/27 10:13:22
     */
    public  static  final String INCORRECT_FORMAT = "格式不正确";

    /**
     * @description: ip 正则
     * @author: Rong.Jia
     * @date 2019/02/27 10:13:22
     */
    public static final String REG_IP = "(?=(\\b|\\D))(((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{1,2})|(2[0-4]\\d)|(25[0-5]))(?=(\\b|\\D))";

    /**
     * @description: 车牌正则：普通车牌，警车及港澳台，教练车
     * @author: Rong.Jia
     * @date 2019/02/13 10:13:22
     */
    public static final String PLATE_TEXT_REG = "(^[\u4E00-\u9FA5]{1}[A-Z0-9]{7}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)";
    //    public static final String PLATETEXTREG = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}[黑黄蓝绿]{1}$";

    /**
     * 身份证正则
     */
    public static final String ID_CARD_REG = "(\\d{4})\\d{10}(\\d{4})";

    /**
     * 密码匹配正则
     */
    public static final String PWD_REG = "^[A-Za-z0-9]{6,16}$";

    /**
     * 电话正则匹配
     */
    public static final String MOBILE_REG = "^0?(13[0-9]|14[5-9]|15[012356789]|166|17[0-8]|18[0-9]|19[8-9])[0-9]{8}$";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REG = "^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$";



}
