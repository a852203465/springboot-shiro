package com.unionman.shiro.common.dto;

import com.unionman.shiro.common.domain.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description: 权限列表信息dto 对象
 * @date 2019/04/17 14:16:22
 * @author Rong.Jia
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("权限列表参数对照 对象")
public class PermissionDTO extends Base implements Serializable {

    private static final long serialVersionUID = -7617361431229885763L;

    /**
     * 资源名称
     */
    @ApiModelProperty(value = "资源名称(必填)", required = true)
    private String name;

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    @ApiModelProperty(value = "权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view(必填)", required = true)
    private String permission;

}
