package com.unionman.shiro.common.dto;

import com.unionman.shiro.common.constants.RegularVerifyConstant;
import com.unionman.shiro.common.domain.Base;
import com.unionman.shiro.paramcheck.validator.groupvlidator.UserInfoGroupValidator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @description: 用户信息dto 对象
 * @date 2019/04/17 14:16:22
 * @author Rong.Jia
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户信息参数对照 对象")
public class UserInfoDTO extends Base implements Serializable {

    private static final long serialVersionUID = -5268465359528714604L;

    /**
     * 名称（昵称或者真实姓名）
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号(必填), 编辑时不可修改", required = true)
    @NotBlank(message = "账号不能为空", groups = UserInfoGroupValidator.class)
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码(修改时非必填) 长度限制 6~16位", required = true)
    @Pattern(regexp = RegularVerifyConstant.PWD_REG, groups = UserInfoGroupValidator.class, message = "密码格式不正确,或者密码长度不足6位 / 长度超出限制")
    private String password;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "联系方式")
    @Pattern(regexp = RegularVerifyConstant.MOBILE_REG, groups = UserInfoGroupValidator.class, message = "电话号码格式不正确")
    private String mobile;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Pattern(regexp = RegularVerifyConstant.EMAIL_REG, groups = UserInfoGroupValidator.class, message = "邮箱格式不正确")
    private String email;

    /**
     * 企业名
     */
    @ApiModelProperty(value = "企业名(必填)", required = true)
    @NotBlank(message = "企业名不能为空", groups = UserInfoGroupValidator.class)
    private String enterprise;

    /**
     *  单位类型id, 单位类型信息主键
     */
    @ApiModelProperty(value = "单位类型id, 单位类型信息主键", required = true)
    @NotNull(message = "单位类型id, 单位类型信息主键", groups = UserInfoGroupValidator.class)
    private Integer unitTypeId;

    /**
     *  经度
     */
    @NotNull(message = "经度不能为空", groups = UserInfoGroupValidator.class)
    @ApiModelProperty(value = "经度", required = true)
    private Double longitude;

    /**
     *  纬度
     */
    @NotNull(message = "纬度不能为空", groups = UserInfoGroupValidator.class)
    @ApiModelProperty(value = "纬度", required = true)
    private Double latitude;

    /**
     *  警务区信息id
     */
    @NotNull(message = "警务区信息id不能为空", groups = UserInfoGroupValidator.class)
    @ApiModelProperty(value = "警务区信息id, 警务区信息id主键", required = true)
    private Integer policeId;


}
