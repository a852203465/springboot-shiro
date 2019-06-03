package com.unionman.shiro.common.enums;

import org.springframework.http.HttpStatus;

/**
 * @description: 数据信息状态枚举类
 * @author Rong.Jia
 * @date 2019/4/2
 */
public enum ResponseEnum {

    /**
     * 0 表示返回成功
     */
    SUCCESS(0,"成功"),
    ERROR(-1, "失败"),

    /**
     * 表示服务器异常 报错提示
     */
    SYSTEM_ERROR(-1, "系统错误"),
    INT404_NOT_FOUND(-1,"找不到请求接口"),
    INT400_BAD_REQUEST(-1,"请求参数或方式错误"),
    IMAGE_PARAM_ERROR(-1, "图片参数错误"),
    FILE_READ_ERROR(-1,"文件读取错误"),
    FILE_WRITE_ERROR(-1,"文件写入失败"),
    FILE_UNZIP_OR_ZIP_ERROR(-1,"文件解压缩失败"),
    FILE_TYPE_CONVER_ERROR(-1,"文件类型转换失败"),
    FILE_NOT_EXIST(-1,"文件不存在"),
    FILE_LIMIT_EXCEEDED(-1, "文件超出限制, 请选择较小文件"),
    FILE_ALREADY_EXISTS(-1,"文件已存在"),

    ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED.value(),"access_token无效"),
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED.value(),"refresh_token无效"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "无权访问(未授权)"),
    AUTHORIZATION_EXPIRES(HttpStatus.UNAUTHORIZED.value(), "授权过期, 请求重新登录"),
    NOT_LOGGED_IN(HttpStatus.UNAUTHORIZED.value(), "未登录"),
    ANONYMOUS_SUBJECT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "无权访问:当前用户是匿名用户，请先登录"),

    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "请求接口不存在"),

    PARAMETER_ERROR(512, "参数不正确"),
    USER_HAS_BEEN_AUTHORIZED(513,"用户已经授权角色"),

    REQUEST_MODE_ERROR(1005, "请求方式错误, 请检查"),
    REQUEST_PARAMETER_FORMAT_IS_INCORRECT(1006, "请求参数格式不正确"),
    MEDIA_TYPE_NOT_SUPPORTED(1007, "媒体类型不支持"),
    ENCRYPTION_OR_DECRYPTION_FAILED(1008, "加密/解密失败"),

    WEBSOCKET_CONNECTION_FAILED(1009, "websocket 连接失败， 请重试"),
    WEBSOCKET_CONNECTION_SUCCEEDED(SUCCESS.getCode(), "websocket 连接成功"),
    WEBSOCKET_PUSH_FAILS(1011, "websocket 数据推送失败"),
    WEBSOCKET_CLOSE_SUCCEEDED(SUCCESS.getCode(), "websocket 已关闭"),
    WEBSOCKET_CLOSE_FAILED(1013, "websocket 关闭失败， 请重试"),
    WEBSOCKET_HEART_KEEP(SUCCESS.getCode(), "heart_keep"),
    WEBSOCKET_USER_ID_NO_NULL(1014, "连接websocket 必须指定userId"),

    SUBJECT_UNAUTHORIZED(3001, "无权访问:当前用户没有此请求所需权限"),

    THE_ID_CANNOT_BE_EMPTY(5000, "id 不能为空"),
    THE_ID_NUMBER_CANNOT_BE_EMPTY(5001, "身份证号不能为空"),
    THE_LIST_OF_PEOPLE_INFO_EMPTY(5002,"人员信息列表为空"),
    THE_ENTERPRISE_NAME_CANNOT_BE_EMPTY(5003, "企业名不能为空"),
    ENTERPRISE_INFO_ALREADY_EXISTS(5004, "企业信息已存在"),
    ENTERPRISE_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED(5005, "企业信息不存在或已被删除"),
    THE_ENTERPRISE_INFO_LIST_IS_EMPTY(5006, "企业信息列表为空"),
    PEOPLE_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED(5007, "人员信息不存在或已被删除"),
    DEVICE_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED(5008, "设备信息不存在或已被删除"),
    THE_LIST_OF_DEVICE_INFO_EMPTY(5009,"设备信息列表为空"),
    ENTERPRISE_HAS_A_DEVICE_ASSOCIATED_WITH_IT_AND_CANNOT_BE_DELETED(5010, "企业有关联设备，不可删除"),
    THE_DEVICE_NAME_CANNOT_BE_EMPTY(5011, "设备名不能为空"),
    THE_LIST_OF_USER_INFO_IS_EMPTY(5012, "用户信息为空"),
    PERMISSION_RESOURCE_NAME_CANNOT_BE_EMPTY(5013, "权限资源名不能为空"),
    THE_LIST_OF_PERMISSION_IS_EMPTY(5014, "权限信息为空"),
    THE_LIST_OF_ROLE_IS_EMPTY(5015, "角色信息为空"),
    ACCOUNT_ALREADY_EXISTS(5016, "账号已存在"),
    PASSWORD_LENGTH_LIMIT_EXCEEDED(5017, "密码长度超过限制"),
    SYSTEM_ADMINISTRATOR_CANNOT_DELETE(5018,"系统管理员不能删除"),
    CURRENT_USER_CANNOT_DELETE(5019,"当前用户不能删除, 该用户为当前登录用户"),
    USERINFO_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED(5020, "用户信息不存在或已被删除"),
    PERMISSION_ALREADY_EXISTS(5021, "权限信息已存在"),
    PERMISSION_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED(5021, "权限信息不存在或已被删除"),
    SYSPERMISSION_RELATED_USER(5022,"权限下有关联子权限，不可删除"),
    SYSPERMISSION_RELATED_SYSROLE(5023, "权限已关联角色，不可删除"),
    ROLE_ALREADY_EXISTS(5024, "角色已存在"),
    ROLE_INFO_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED(5025, "角色信息不存在或已被删除"),
    DATA_QUOTE(5026, "数据被引用，无法执行操作"),
    ROLE_HAS_AUTHORIZED_MENU(5027,"角色已经授权菜单"),
    ACCOUNT_DOES_NOT_EXIST(5028, "账号不存在"),
    ACCOUNT_IS_EMPTY(5029, "账号为空"),
    WRONG_ACCOUNT_OR_PASSWORD(5030, "账号或密码错误"),
    ACCOUNT_AUTOMATIC_LOGOUT(5031, "账号已自动退出登录，无需再次退出登录"),
    PASSWORD_CHECK_FAILED(5032, "密码校验失败, 请求检查原密码是否输入正确"),
    THE_OLD_PASSWORD_IS_THE_SAME_AS_THE_NEW_ONE(5033, "旧密码与新密码相同, 请重新输入新密码"),
    PLATFORM_ADMIN_CANNOT_BE_MODIFIED_BY_AFFILIATES(5034, "平台管理员不能被修改关联企业"),
    ENTERPRISES_HAVE_ASSOCIATED_PERSONNEL_CAN_NOT_BE_DELETED(5035, "企业有关联人员，不可删除"),
    DATA_DICTIONARYLIST_NULL(5036,"数据字典列表为空"),
    DATA_DICTIONARY_CATEGORY_NOT_NULL(5037,"数据字典的类别不能为空"),
    DATA_DICTIONARY_NUMERICAL_NOT_NULL(5038,"数据字典的数值不能为空"),
    THE_PHONE_CANNOT_BE_EMPTY(5039, "联系电话不能为空"),
    THE_EMPLOYEE_JOB_NUMBER_CANNOT_BE_EMPTY(5040, "员工工号不能为空 "),
    THE_PHONE_ALREADY_EXISTS(5041, "联系电话已存在"),
    EMPLOYEE_ID_ALREADY_EXISTS(5042, "员工工号已存在"),
    THE_NAME_OF_THE_POLICE_DISTRICT_CANNOT_BE_EMPTY(5043, "警务区名不能为空"),
    POLICE_AREA_NAME_INFORMATION_ALREADY_EXISTS(5044, "警务区名信息已存在"),
    POLICE_INFORMATION_DOES_NOT_EXIST_OR_HAS_BEEN_DELETED(5045, "警务区信息不存在或已删除"),
    POLICE_INFORMATION_LIST_IS_EMPTY(5046, "警务区信息列表为空"),
    POLICE_AFFAIRS_HAS_ASSOCIATED_ENTERPRISE_CANNOT_DELETE(5047, "警务区有关联企业，不可删除"),



    THE_UNIT_TYPE_NAME_CANNOT_BE_EMPTY(5050, "单位类型名不能为空"),
    UNIT_TYPE_INFORMATION_ALREADY_EXISTS(5051, "单位类型信息已存在"),
    UNIT_TYPE_HAS_ASSOCIATED_ENTERPRISE_CANNOT_DELETE(5052, "单位类型有关联企业，不可删除"),







    ;

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
