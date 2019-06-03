package com.unionman.shiro.common.domain;

import com.unionman.shiro.paramcheck.validator.groupvlidator.IdGroupValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description: 实体类，dto 父类
 * @author Rong.Jia
 * @date 2019/02/19 18:01:22
 */
@Data
@MappedSuperclass
public class Base implements Serializable {

    private static final long serialVersionUID = -7519418012137093264L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    @NotNull(groups = IdGroupValidator.class, message = "id 不能为空")
    public Integer id;


    @ApiModelProperty("修改人")
    protected String updatedUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    protected Long updatedTime;

    /**
     * 添加时间
     */
    @ApiModelProperty("添加时间")
    protected Long createdTime;

    /**
     * 添加人
     */
    @ApiModelProperty("添加人")
    protected String createdUser;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    protected String description;

    /**
     *  设备id
     */
    @Transient
    @ApiModelProperty(hidden = true)
    private String device_id;

    /**
     * 协议版本
     */
    @Transient
    @ApiModelProperty(hidden = true)
    private String version;

    /**
     * 客户端时间戳
     */
    @Transient
    @ApiModelProperty(hidden = true)
    private String timestamp;

    /**
     * 根据约定算法生成的参数签名
     */
    @Transient
    @ApiModelProperty(hidden = true)
    private String sign;

}
