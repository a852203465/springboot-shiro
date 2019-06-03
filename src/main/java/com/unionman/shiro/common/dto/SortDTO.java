package com.unionman.shiro.common.dto;

import com.unionman.shiro.common.constants.CommonConstant;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @description:  排序Dto对象
 * @author Rong.Jia
 * @date 2018/6/20 10:56
 */
@ToString
@EqualsAndHashCode
public class SortDTO {

    /**
     * 排序方式
     */
    private String orderType;

    /**
     * 排序字段
     */
    private String orderField;

    public SortDTO() {
        super();
    }

    public SortDTO(String orderType, String orderField) {
        this.orderType = StringUtils.isBlank(orderType) ? CommonConstant.DEFAULT_ORDER_TYPE : orderType;
        this.orderField = StringUtils.isBlank(orderField) ? CommonConstant.DEFAULT_ORDER_FIELD : orderField;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = StringUtils.isBlank(orderType) ? CommonConstant.DEFAULT_ORDER_TYPE : orderType;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = StringUtils.isBlank(orderField) ? CommonConstant.DEFAULT_ORDER_FIELD : orderField;
    }
}
