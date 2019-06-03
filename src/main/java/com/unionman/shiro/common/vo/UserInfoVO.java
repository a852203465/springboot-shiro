package com.unionman.shiro.common.vo;

import com.unionman.shiro.common.dto.UserInfoDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @descrption: 用户信息Vo
 * @author Ring.Jia
 * @date 2019/04/17 16:19:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户信息 对照对象")
public class UserInfoVO extends UserInfoDTO implements Serializable {

    private static final long serialVersionUID = 318872322607252731L;

    /**
     * 用户 -- 角色 ： 多对多
     * 立即从数据库中进行加载数据;
     */
    @ApiModelProperty("角色")
    private RoleVO role;

}
