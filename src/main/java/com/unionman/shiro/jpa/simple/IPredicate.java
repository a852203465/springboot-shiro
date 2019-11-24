package com.unionman.shiro.jpa.simple;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * 条件查询接口
 * @author Rong.Jia
 * @date 2019/08/06 11:43:22
 */
public interface IPredicate {

    enum Operator {

        // 添加枚举
        EQ, NE, LIKE, GT, LT, GTE, LTE, AND, OR, IS_MEMBER, IS_NOT_MEMBER, BETWEEN, NOT_LIKE, JOIN_LIKE, JOIN_EQ, JOIN_NE, JOIN_BETWEEN, IS_NULL, IS_NOT_NULL
    }

    Predicate toPredicate(Root<?> root, CriteriaBuilder builder);
}
