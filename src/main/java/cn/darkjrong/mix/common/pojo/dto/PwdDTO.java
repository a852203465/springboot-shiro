package cn.darkjrong.mix.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改密码dto 对象
 *
 * @author Rong.Jia
 * @date 2022/03/02
 */
@Data
@ApiModel("修改密码参数对照 对象")
public class PwdDTO implements Serializable {

    private static final long serialVersionUID = 3482634779913187319L;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "新密码 不能为空")
    private String newPwd;

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码",required = true)
    @NotBlank(message = "旧密码不能空")
    private String oldPwd;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", required = true)
    @NotBlank(message = "用户账号不能为空")
    private String account;

}
