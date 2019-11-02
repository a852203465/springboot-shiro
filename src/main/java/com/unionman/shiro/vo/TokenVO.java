package com.unionman.shiro.vo;

import com.unionman.shiro.domain.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @description: token返回对象
 * @date 2019/04/15 15:03:22
 * @author Rong.Jia
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TokenVO extends Base implements Serializable {

    private static final long serialVersionUID = -4976155090384163915L;

    private String token;

}
